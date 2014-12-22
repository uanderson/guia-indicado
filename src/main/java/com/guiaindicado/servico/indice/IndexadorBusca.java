package com.guiaindicado.servico.indice;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.facet.index.CategoryDocumentBuilder;
import org.apache.lucene.facet.taxonomy.CategoryPath;
import org.apache.lucene.facet.taxonomy.InconsistentTaxonomyException;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.SearcherManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.guiaindicado.busca.Buscador;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.avaliacao.RepositorioAvaliacao;
import com.guiaindicado.dominio.avaliacao.SinteseAvaliacao;
import com.guiaindicado.dominio.item.Categoria;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.item.ImagemEmpresa;
import com.guiaindicado.dominio.item.RepositorioEmpresa;
import com.guiaindicado.dominio.item.RepositorioImagem;
import com.guiaindicado.suporte.Pagina;

/**
 * Processa e indexa as empresas/produtos/serviços disponíveis no site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class IndexadorBusca {

    private static final Logger LOG = LoggerFactory.getLogger(Buscador.class);
    
    @Autowired @Qualifier("escritorIndiceBusca") IndexWriter escritorIndice;
    @Autowired @Qualifier("escritorTaxonomiaBusca") TaxonomyWriter escritorTaxonomia;
    @Autowired @Qualifier("leitorTaxonomiaBusca") TaxonomyReader leitorTaxonomia;
    @Autowired @Qualifier("gerenciadorPesquisa") SearcherManager gerenciadorPesquisa;
    
    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private RepositorioImagem respositorioImagem;
    @Autowired private RepositorioAvaliacao repositorioAvaliacao;
    
    /**
     * Indexa todas as empresas/produtos/serviços existente na base. A execução dessa operação é
     * recomendada apenas em casos em que o índice precise ser recriado. Se houver um número muito
     * grande itens, pode gerar lentidão e deve ser rodado em horários alternativos ao maior acesso
     * do site.
     * 
     * Todos os itens são removidos antes do índice e só depois os novos são adicionados.
     * 
     * @return Sucesso ou falha da operação
     */
    @Transactional(readOnly = true)
    public Resultado indexarBusca() {
        try {
            escritorIndice.deleteAll();
            
            CategoryDocumentBuilder construtorCategoria = new CategoryDocumentBuilder(escritorTaxonomia);

            int total = repositorioEmpresa.contar(Empresa.Status.MODERADO);
            int totalProcessado = 0;
            
            LOG.info("Iniciando o processamento de {} empresas", total);
            
            do {
                Collection<Empresa> empresas = repositorioEmpresa.listar(
                    Empresa.Status.MODERADO, Pagina.criar(totalProcessado, 100));
                
                for (Empresa empresa : empresas) {
                    escritorIndice.addDocument(criarDocumento(empresa, construtorCategoria));
                }
                
                totalProcessado += empresas.size();
            } while (totalProcessado < total);
            
            escritorTaxonomia.commit();
            escritorIndice.commit();
            leitorTaxonomia.refresh();
            gerenciadorPesquisa.maybeRefresh();
            
            String mensagem = MessageFormat.format("{0} empresas foram indexadas", total);
            LOG.info(mensagem);
            
            return Resultado.sucesso(mensagem);
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
            return Resultado.erro("Não foi possível realizar a indexação. Tente mais tarde.");
        } catch (InconsistentTaxonomyException ex) {
            LOG.error(ex.getMessage(), ex);
            return Resultado.erro("Não foi possível realizar a indexação. Tente mais tarde.");
        }
    }
    
    /**
     * Adiciona/atualiza uma empresa no índice. Caso a empresa não exista, o documento será
     * adicionado; Caso exista o documento será atualizado (excluído e adicionado).
     * 
     * A empresa fica imediatamente disponível após a realização dessa operação.
     * 
     * @param empresa Empresa a ser adicionada ao índice
     * @return Sucesso ou falha da operação
     */
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Resultado indexarEmpresa(Empresa empresa) {
        try {
            CategoryDocumentBuilder construtorCategoria = new CategoryDocumentBuilder(escritorTaxonomia);
            
            String idEmpresa = String.valueOf(empresa.getId());
            Term termo = new Term("uid", "emp-" + idEmpresa);
            
            escritorIndice.updateDocument(termo, criarDocumento(empresa, construtorCategoria));
            escritorTaxonomia.commit();
            escritorIndice.commit();
            leitorTaxonomia.refresh();
            gerenciadorPesquisa.maybeRefresh();
            
            return Resultado.sucesso();
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
            return Resultado.erro();
        } catch (InconsistentTaxonomyException ex) {
            LOG.error(ex.getMessage(), ex);
            return Resultado.erro();
        }
    }

    /**
     * Cria um documento lucene para indexar baseado no item passado por parâmetro.
     * 
     * @param empresa Item a ser transformado
     * @param construtor Gerador das categorias
     * @return Documento do lucene
     * @throws IOException
     */
    private Document criarDocumento(Empresa empresa, CategoryDocumentBuilder construtor) 
            throws IOException {
        
        String idUnico = "emp-" + empresa.getId();
        String idCidade = String.valueOf(empresa.getCidade().getId());
        Categoria categoria = empresa.getCategoria();

        ImagemEmpresa imagem = respositorioImagem.getImagemPrincipal(empresa);
        String imagemPrincipal = (imagem == null) ? "" : imagem.criarArquivo().getNome();
        
        if (!imagemPrincipal.isEmpty()) {
            imagemPrincipal = imagemPrincipal.substring(0, imagemPrincipal.lastIndexOf("."));
        }
        
        Document documento = new Document();
        documento.add(new StringField("uid", idUnico, Store.NO));
        documento.add(new StringField("id", String.valueOf(empresa.getId()), Store.YES));
        documento.add(new StringField("referencia", empresa.getReferencia(), Store.YES));
        documento.add(new StringField("local", idCidade, Store.NO));
        documento.add(new StringField("categoria", categoria.getReferencia(), Store.NO));
        documento.add(new TextField("nome", empresa.getNome(), Store.YES));
        documento.add(new TextField("descricao", empresa.getDescricao(), Store.NO));
        documento.add(new TextField("tags", empresa.getTags(), Store.NO));
        documento.add(new StringField("endereco", empresa.getEndereco(), Store.YES));
        documento.add(new StringField("numero", empresa.getNumero(), Store.YES));
        documento.add(new StringField("bairro", empresa.getBairro(), Store.YES));
        documento.add(new StringField("telefone", empresa.getTelefone(), Store.YES));
        documento.add(new StringField("tipo", "empresa", Store.YES));
        documento.add(new StringField("imagem", imagemPrincipal, Store.YES));
        
        SinteseAvaliacao sintese = repositorioAvaliacao.sintetisar(empresa.getId());
        
        int total = sintese.getTotal();
        int media = sintese.getMedia();
        
        documento.add(new IntField("avaliacoes", total, Store.YES));
        documento.add(new IntField("mediaAvaliacao", media, Store.YES));
        
        CategoryPath nomeCategoria = new CategoryPath(
            "categoria", categoria.getReferencia() + "," + categoria.getNome());
        
        Collection<CategoryPath> categorias = Lists.newArrayList(nomeCategoria);
        construtor.setCategoryPaths(categorias).build(documento);
        
        return documento;
    }
}
