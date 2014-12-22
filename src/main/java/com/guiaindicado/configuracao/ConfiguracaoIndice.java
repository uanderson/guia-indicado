package com.guiaindicado.configuracao;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.br.BrazilianAnalyzer;
import org.apache.lucene.analysis.core.KeywordAnalyzer;
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
import org.apache.lucene.facet.taxonomy.TaxonomyReader;
import org.apache.lucene.facet.taxonomy.TaxonomyWriter;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import com.google.common.collect.Maps;

@Configuration
public class ConfiguracaoIndice {

    @Value("#{environment['site.fs.indice.busca']}") String diretorioIndiceBusca;
    @Value("#{environment['site.fs.indice.busca.taxonomia']}") String diretorioTaxonomiaBusca;
        
    @Bean
    public Analyzer analisadorIndiceBusca() {
        BrazilianAnalyzer analisadorBrasileiro = new BrazilianAnalyzer(Version.LUCENE_40);
        KeywordAnalyzer analisadorPalavraChave = new KeywordAnalyzer();
        
        Map<String, Analyzer> analisadores = Maps.newHashMap();
        analisadores.put("categoria", analisadorPalavraChave);
        
        return new PerFieldAnalyzerWrapper(analisadorBrasileiro, analisadores);
    }
    
    @Bean
    @DependsOn("analisadorIndiceBusca")
    public FSDirectory diretorioIndiceBusca() throws IOException {
        File diretorio = new File(diretorioIndiceBusca);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        return FSDirectory.open(diretorio);
    }
    
    @Bean
    @DependsOn("analisadorIndiceBusca")
    public FSDirectory diretorioTaxonomiaBusca() throws IOException {
        File diretorio = new File(diretorioTaxonomiaBusca);

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

        return FSDirectory.open(diretorio);
    }
    
    @Bean
    @DependsOn("diretorioIndiceBusca")
    public IndexWriter escritorIndiceBusca() throws IOException {
        IndexWriterConfig configuracao = 
            new IndexWriterConfig(Version.LUCENE_40, analisadorIndiceBusca());
        
        configuracao.setOpenMode(OpenMode.CREATE_OR_APPEND);
        
        return new IndexWriter(diretorioIndiceBusca(), configuracao);
    }
    
    @Bean
    @DependsOn("escritorIndiceBusca")
    public SearcherManager gerenciadorPesquisa() throws IOException {
        return new SearcherManager(escritorIndiceBusca(), true, null);
    }
    
    @Bean
    @DependsOn("escritorIndiceBusca")
    public IndexReader leitorIndiceBusca() throws IOException {
        return DirectoryReader.open(diretorioIndiceBusca());
    }
    
    @Bean
    @DependsOn("diretorioTaxonomiaBusca")
    public TaxonomyWriter escritorTaxonomiaBusca() throws IOException {
        return new DirectoryTaxonomyWriter(diretorioTaxonomiaBusca());
    }
    
    @Bean
    @DependsOn("escritorTaxonomiaBusca")
    public TaxonomyReader leitorTaxonomiaBusca() throws IOException {
        return new DirectoryTaxonomyReader(diretorioTaxonomiaBusca());
    }
}