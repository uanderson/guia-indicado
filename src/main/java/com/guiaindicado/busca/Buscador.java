package com.guiaindicado.busca;

import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.facet.index.params.DefaultFacetIndexingParams;
import org.apache.lucene.facet.index.params.FacetIndexingParams;
import org.apache.lucene.facet.search.FacetsCollector;
import org.apache.lucene.facet.search.params.CountFacetRequest;
import org.apache.lucene.facet.search.params.FacetRequest;
import org.apache.lucene.facet.search.params.FacetSearchParams;
import org.apache.lucene.facet.search.results.FacetResult;
import org.apache.lucene.facet.search.results.FacetResultNode;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MultiCollector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.SimpleSpanFragmenter;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.ResultadoPaginado;
import com.guiaindicado.ui.modelo.Categoria;
import com.guiaindicado.ui.modelo.ItemBusca;
import com.guiaindicado.ui.modelo.ResultadoBusca;

/**
 * Realiza as operações de busca.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class Buscador {

    private static final Logger LOG = LoggerFactory.getLogger(Buscador.class);
    
    @Autowired @Qualifier("leitorTaxonomiaBusca") TaxonomyReader leitorTaxonomia;
    @Autowired @Qualifier("gerenciadorPesquisa") SearcherManager gerenciadorPesquisa;
    @Autowired @Qualifier("analisadorIndiceBusca") Analyzer analisador;
    
    /**
     * Realiza a busca baseado nos dados do parâmetro passado. A busca pode ser realizada
     * de três forma distintas: 1) Por termo específico; 2) Por termo específico mais
     * categoria; 3) Por categoria apenas.
     * 
     * @param busca Informação para a busca
     * @return Resultado da busca
     */
    public ResultadoBusca buscar(Busca busca) {
        IndexSearcher pesquisador = gerenciadorPesquisa.acquire();
        
        try {
            Query query = criarQuery(busca);
            TopDocsCollector<?> coletor = busca.criarColetor();
            FacetsCollector coletorTaxonomia = criarColetorTaxonomia(pesquisador.getIndexReader());
            
            pesquisador.search(query, MultiCollector.wrap(coletor, coletorTaxonomia));

            ScoreDoc[] documentos = coletor.topDocs().scoreDocs;
            Collection<ItemBusca> resultados = Lists.newArrayList();
            
            Pagina pagina = busca.criarPagina(coletor);
            int ultimoItemPagina = busca.getUltimoItemPagina(coletor);
            Highlighter realcadorResultado = criarRealcadorResultado(query);
            
            Document documento = null;
            ItemBusca item = null;
            
            for (int indice = pagina.getInicio(); indice < ultimoItemPagina; indice++) {
                documento = pesquisador.doc(documentos[indice].doc);
                item = criarItem(documento);
                
                TokenStream stream = TokenSources.getAnyTokenStream(pesquisador.getIndexReader(),
                    documentos[indice].doc, "nome", documento, analisador);
                
                String nomeRealcado = realcadorResultado
                    .getBestFragment(stream, documento.get("nome"));
                
                if (nomeRealcado != null) {
                    item.setNome(nomeRealcado);
                }
                
                resultados.add(item);
            }

            ResultadoPaginado<ItemBusca> resultadoPaginado = 
                ResultadoPaginado.criar(pagina, resultados, coletor.getTotalHits(), 
                    Busca.NUMERO_MAXIMO_RESULTADOS);
            
            ResultadoBusca resultadoBusca = new ResultadoBusca();
            resultadoBusca.setResultado(resultadoPaginado);
            resultadoBusca.setCategorias(
                totalizarCategorias(busca, coletorTaxonomia.getFacetResults()));
            
            return resultadoBusca;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (InvalidTokenOffsetsException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                gerenciadorPesquisa.release(pesquisador);
            } catch (IOException ex) {
                LOG.error(ex.getMessage(), ex);
            }
        }
    }

    /**
     * Cria um realçador para a busca. Esse realçador irá colocar um HTML específico na palavra
     * realçada.
     * 
     * @param query Query da busca
     * @return Realcador
     */
    private Highlighter criarRealcadorResultado(Query query) {
        SimpleHTMLFormatter formatador = new SimpleHTMLFormatter(
            "<span class=\"highlight\">", "</span>");
        
        QueryScorer pontuador = new QueryScorer(query);
        Fragmenter fragmentador = new SimpleSpanFragmenter(pontuador);
        
        Highlighter realcador = new Highlighter(formatador, pontuador);
        realcador.setFragmentScorer(pontuador);
        realcador.setTextFragmenter(fragmentador);
        
        return realcador;
    }

    /**
     * Transforma um documento do índice em um resultado de busca para ser exibido no site.
     * 
     * @param documento Documento indexado
     * @return Novo item
     */
    private ItemBusca criarItem(Document documento) {
        ItemBusca item = new ItemBusca();
        item.setId(Integer.valueOf(documento.get("id")));
        item.setNome(documento.get("nome"));
        item.setTipo(documento.get("tipo"));
        item.setReferencia(documento.get("referencia"));
        item.setEndereco(documento.get("endereco"));
        item.setNumero(documento.get("numero"));
        item.setBairro(documento.get("bairro"));
        item.setTelefone(documento.get("telefone"));
        item.setMediaAvaliacao(Integer.valueOf(documento.get("mediaAvaliacao")));
        item.setAvaliacoes(Integer.valueOf(documento.get("avaliacoes")));
        item.setImagemPrincipal(documento.get("imagem"));
        
        return item;
    }

    /**
     * Cria a query para ser utilizada como filtro de busca.
     * 
     * @param busca Dados da busca
     * @return Query apropriada para filtro
     */
    private Query criarQuery(Busca busca) {
        String termo = busca.getTermoNormalizado();
        String categoria = busca.getCategoriaHifenizada();
        String local = String.valueOf(busca.getLocal());
        
        StringBuilder sb = new StringBuilder();
        
        if (!categoria.isEmpty()) {
            sb.append("+categoria:");
            sb.append(categoria);
        }
        
        if (!termo.isEmpty()) {
            sb.append(" +(nome:\"");
            sb.append(termo);
            sb.append("\" tags:\"");
            sb.append(termo);
            sb.append("\" descricao:\"");
            sb.append(termo);
            sb.append("\")");
        }

        sb.append(" +local:");
        sb.append(local);

        try {
            return new QueryParser(Version.LUCENE_40, null, analisador).parse(sb.toString());
        } catch (ParseException ex) {
            LOG.error(ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }
    
    /**
     * Obtém as categorias onde há ocorrência para o termo com total por categoria. 
     * 
     * @param resultadoTaxonomia Dados taxonômicos
     * @return Coleção de categorias
     */
    private Collection<Categoria> totalizarCategorias(Busca busca, Collection<FacetResult> resultadoTaxonomia) {
        Collection<Categoria> categorias = Lists.newArrayList();
        
        if (!busca.getAgruparCategoria()) {
            return categorias;
        }
        
        for (FacetResult resultado : resultadoTaxonomia) {
            FacetResultNode noResultado = resultado.getFacetResultNode();

            for (FacetResultNode node : noResultado.getSubResults()) {
                String rotulo = node.getLabel().lastComponent();
                int total = (int) node.getValue();
                
                String[] rotulos = rotulo.split(",");
                
                Categoria categoria = new Categoria();
                categoria.setTotal(total);
                categoria.setReferencia(rotulos[0]);
                categoria.setNome(rotulos[1]);
                
                categorias.add(categoria);
            }
        }
        
        return categorias;
    }
    
    /**
     * Obtém uma instância do coletor taxonômico.
     * 
     * @param leitor Leitor do índice
     * @return Novo coletor taxonômico
     * @throws IOException
     */
    private FacetsCollector criarColetorTaxonomia(IndexReader leitor) throws IOException {
        CategoryPath categoria = new CategoryPath("categoria");
        FacetIndexingParams parametrosIndice = new DefaultFacetIndexingParams();
        FacetSearchParams parametrosTaxonomia = new FacetSearchParams(parametrosIndice);
        
        FacetRequest requisicaoPesquisa = new CountFacetRequest(categoria, 10);
        parametrosTaxonomia.addFacetRequest(requisicaoPesquisa);
        
        return new FacetsCollector(parametrosTaxonomia, leitor, leitorTaxonomia);
      }
}
