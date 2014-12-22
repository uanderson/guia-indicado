package com.guiaindicado.pesquisa;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.guiaindicado.suporte.Strings;
import com.guiaindicado.ui.modelo.Cidade;

/**
 * Realiza a pesquisa de cidades de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaCidade {

    @Value("#{environment['site.fs.indice.cidade']}") String diretorioIndice;

    /**
     * Pesquisa a cidade por nome.
     * 
     * @param nome Nome da cidade
     * @return Coleção de cidades
     */
    public Collection<Cidade> pesquisarPorNome(String nome) {
        try {
            IndexReader leitor = criarLeitorIndice();
            IndexSearcher pesquisador = new IndexSearcher(leitor);
            
            String nomeBusca = Strings.removerAcentoEspecial(nome).toLowerCase();
            
            BooleanQuery query = new BooleanQuery();
            query.add(new TermQuery(new Term("nomeBusca", nomeBusca)), BooleanClause.Occur.SHOULD);
            query.add(new WildcardQuery(new Term("nomeBusca", nomeBusca + "*")), BooleanClause.Occur.SHOULD);
            
            TopScoreDocCollector coletor = TopScoreDocCollector.create(5, true);
            pesquisador.search(query, coletor);

            ScoreDoc[] resultados = coletor.topDocs().scoreDocs;
            Collection<Cidade> cidades = Sets.newLinkedHashSet();

            for (ScoreDoc resultado : resultados) {
                Document documento = pesquisador.doc(resultado.doc);
                Cidade cidade = new Cidade();
                cidade.setId(Integer.valueOf(documento.get("id")));
                cidade.setNome(documento.get("nome"));
                cidade.setSiglaEstado(documento.get("siglaEstado"));

                cidades.add(cidade);
            }

            leitor.close();

            return cidades;
        } catch (Exception ex) {
            return ImmutableList.of();
        }
    }

    /**
     * Busca a cidade por id.
     * 
     * @param id Id da cidade
     * @return Cidade caso seja encontrada, null caso contrário
     */
    public Optional<Cidade> pesquisarPorId(Integer id) {
        try {
            IndexReader leitor = criarLeitorIndice();
            IndexSearcher pesquisador = new IndexSearcher(leitor);
            
            Query query = new TermQuery(new Term("id", String.valueOf(id)));
            TopScoreDocCollector coletor = TopScoreDocCollector.create(1, true);
            pesquisador.search(query, coletor);
            
            ScoreDoc[] resultados = coletor.topDocs().scoreDocs;
            Cidade cidade = null;

            if (resultados.length == 1) {
                Document documento = pesquisador.doc(resultados[0].doc);
                
                cidade = new Cidade();
                cidade.setId(Integer.valueOf(documento.get("id")));
                cidade.setNome(documento.get("nome"));
                cidade.setSiglaEstado(documento.get("siglaEstado"));
            }

            leitor.close();
            return Optional.fromNullable(cidade);
        } catch (Exception ex) {
            return Optional.absent();
        }
    }
    
    /**
     * Cria um novo pesquisador para o índice de cidade.
     * 
     * @return Novo pesquisador
     */
    private IndexReader criarLeitorIndice() throws IOException {
        Directory dirIndice = FSDirectory.open(new File(diretorioIndice));
        return DirectoryReader.open(dirIndice);
    }
}
