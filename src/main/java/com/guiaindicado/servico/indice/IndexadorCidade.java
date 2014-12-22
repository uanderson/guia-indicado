package com.guiaindicado.servico.indice;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.dominio.localizacao.Estado;
import com.guiaindicado.dominio.localizacao.RepositorioCidade;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.Strings;

/**
 * Processa e indexa as cidades disponíveis no site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class IndexadorCidade {
    
    @Value("#{environment['site.fs.indice.cidade']}") String diretorioIndice;

    @Autowired private RepositorioCidade repositorioCidade;
    
    /**
     * Indexa todas as cidades da base no disco. Antes de iniciar a indexação, apaga todas as
     * cidades já indexadas.
     * 
     * @return Sucesso ou falha da ação
     */
    @Transactional(readOnly = true)
    public Resultado indexarCidade() {
        try {
            IndexWriter escritor = criarEscritorIndice();
            
            int totalCidades = repositorioCidade.contar();
            int totalProcessadas = 0;
            
            do {
                Pagina pagina = Pagina.criar(totalProcessadas, 100);
                Collection<Cidade> cidades = repositorioCidade.listar(pagina);
                
                for (Cidade cidade : cidades) {
                    escritor.addDocument(criarDocumento(cidade));
                }
                
                totalProcessadas += cidades.size();
            } while (totalProcessadas < totalCidades);
            
            escritor.close();
            
            return Resultado.sucesso(MessageFormat.format("{0} cidades indexadas.", totalCidades));
        } catch (Exception ex) {
            return Resultado.erro("Não foi possível realizar a indexação. Tente mais tarde.");
        }
    }
    
    /**
     * Cria um documento lucene para indexar.
     * 
     * @param cidade Cidade a ser transformada
     * @return Documento do lucene
     */
    private Document criarDocumento(Cidade cidade) {
        Estado estado = cidade.getEstado();
        String nomeBusca = Strings.removerAcentoEspecial(cidade.getNome()).toLowerCase();
        
        Document documento = new Document();
        documento.add(new StringField("id", String.valueOf(cidade.getId()), Store.YES));
        documento.add(new StringField("nomeBusca", nomeBusca, Store.NO));
        documento.add(new StringField("nome", cidade.getNome(), Store.YES));
        documento.add(new StringField("siglaEstado", estado.getSigla(), Store.YES));
        
        return documento;
    }
    
    /**
     * Cria um novo escritor para armazenar as cidades no índice. 
     * 
     * @return Novo escritor
     * @throws IOException
     */
    private IndexWriter criarEscritorIndice() throws IOException {
        Directory dirIndice = FSDirectory.open(new File(diretorioIndice));
        Analyzer analisador = new BrazilianAnalyzer(Version.LUCENE_40);
        
        IndexWriterConfig configuracao = new IndexWriterConfig(Version.LUCENE_40, analisador);
        configuracao.setOpenMode(OpenMode.CREATE_OR_APPEND);

        return new IndexWriter(dirIndice, configuracao);
    }
}
