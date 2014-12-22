package com.guiaindicado.busca;

import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.Strings;


/**
 * Retém as informações para realização da busca.
 * 
 * @author Uanderson Soares Soares
 */
public class Busca {

    /**
     * Define o local para busca padrão quando nenhum for informado.
     */
    public static final int LOCAL_PADRAO = 412810;

    /**
     * Define qual o tamanho máximo que uma página pode ter.
     */
    public static final int TAMANHO_PAGINA = 8;

    /**
     * Define o número máximo de resultados por busca. O número está definido baixo para
     * evitar ataques e manter o servidor responsível.
     */
    public static final int NUMERO_MAXIMO_RESULTADOS = 32;
    
    private String termo;
    private String categoria;
    private boolean agruparCategoria;
    private int local;
    private int inicio;
    private Ordem ordem;

    /**
     * Constrói uma nova busca baseado no construtor.
     * 
     * @param construtor Construtor com dados da busca
     */
    private Busca(Construtor construtor) {
        termo = construtor.termo;
        categoria = construtor.categoria;
        agruparCategoria = construtor.agruparCategoria;
        
        if (construtor.local == 0 || construtor.local < -1) {
            local = LOCAL_PADRAO;
        } else {
            local = construtor.local;
        }
        
        if (construtor.inicio > NUMERO_MAXIMO_RESULTADOS) {
            inicio = NUMERO_MAXIMO_RESULTADOS;
        } else if (construtor.inicio < 0) {
            inicio = 0;
        } else {
            inicio = construtor.inicio;
        }
        
        ordem = construtor.ordem;
    }

    /**
     * Remove todos os acentos e caracteres especiais do termo e transforma para
     * minúsculo.
     * 
     * @return Termo normalizado
     */
    public String getTermoNormalizado() {
        return Strings.removerAcentoEspecial(termo).toLowerCase();
    }
    
    /**
     * Substitui os espaços entre as palavras do termo por hífen.
     * 
     * @return Termo hifenizado
     */
    public String getTermoHifenizado() {
        return Strings.hifenizar(getTermoNormalizado());
    }
    
    /**
     * Remove todos os acentos e caracteres especiais da categoria e transforma para
     * minúsculo.
     * 
     * @return Categoria normalizada
     */
    public String getCategoriaNormalizada() {
        return Strings.removerAcentoEspecial(categoria).toLowerCase();
    }
    
    /**
     * Substitui os espaços entre as palavras da categoria por hífen.
     * 
     * @return Categoria hifenizada
     */
    public String getCategoriaHifenizada() {
        return Strings.hifenizar(getCategoriaNormalizada());
    }
    
    /**
     * Cria um novo construtor.
     * 
     * @return Novo construtor
     */
    public static Construtor construtor() {
        return new Construtor();
    }
    
    /**
     * Retorna se a busca deve agrupar o total por categorias ou não. O padrão é false. 
     * 
     * @return true caso deva agrupar, false caso contrário
     */
    public boolean getAgruparCategoria() {
        return agruparCategoria;
    }
    
    /**
     * Retorna o local para filtro dos resultados.
     * 
     * @return Local para filtrar resultados
     */
    public int getLocal() {
        return local;
    }
    
    /**
     * Retorna o registro de ínicio para paginação.
     * 
     * @return Ínicio da paginação
     */
    public int getInicio() {
        return inicio;
    }
    
    /**
     * Retorna a ordem da busca.
     * 
     * @return Ordem da busca
     */
    public Ordem getOrdem() {
        return ordem;
    }
    
    /**
     * Cria um coletor para coletar os resultados da busca. O coletor é definido baseado
     * no tipo da ordem. Caso a ordem seja de melhor classificados, o coletor é
     * {@link TopFieldCollector}, caso contrário o padrão será por relevância que usa
     * {@link TopDocsCollector}.
     * 
     * @return Novo coletor de resultados
     * @throws IOException
     */
    public TopDocsCollector<?> criarColetor() throws IOException {
        if (Busca.Ordem.MELHOR_CLASSIFICADO.equals(ordem)) {
            SortField campo = new SortField("mediaClassificacao", SortField.Type.INT);
            return TopFieldCollector.create(new Sort(campo), getNumeroResultados(), true, false, false, false);
        }
        
        return TopScoreDocCollector.create(getNumeroResultados(), true);
    }

    /**
     * Cria um objeto do tipo página para utilização na renderização das páginas dos resultados.
     * 
     * @param coletor Coletor para obter o total de hits.
     * @return Nova página
     */
    public Pagina criarPagina(TopDocsCollector<?> coletor) {
        Pagina pagina = Pagina.criar(inicio, TAMANHO_PAGINA);
        pagina.definirInicio(coletor.getTotalHits());
        
        return pagina;
    }
    
    /**
     * Determina o último item para o total de itens que serão retornados na
     * paginação. Ex: Se o início é definido como 8 e são retornados 32 resultados, o
     * último número da página será 16.
     * 
     * @param coletor Coletor para obter o total de hits
     * @return Último hit válido para paginação
     */
    public int getUltimoItemPagina(TopDocsCollector<?> coletor) {
        return Math.min(getNumeroResultados(), coletor.getTotalHits());
    }
    
    /**
     * Define o número de resultados que o coletor irá coletar.
     * 
     * @return Número de resultados esperados
     */
    private int getNumeroResultados() {
        if ((inicio + TAMANHO_PAGINA) > NUMERO_MAXIMO_RESULTADOS) {
            return NUMERO_MAXIMO_RESULTADOS;
        } else {
            return inicio + TAMANHO_PAGINA;
        }
    }
    
    /**
     * Constrói um objeto busca a partir dos dados passados.
     */
    public static class Construtor {
        
        private String termo = "";
        private String categoria = "";
        private boolean agruparCategoria = false;
        private int local = 0;
        private int inicio = 0;
        private Ordem ordem = Ordem.RELEVANCIA;
        
        public Construtor comTermo(String termo) {
            this.termo = Preconditions.checkNotNull(termo);
            return this;
        }
        
        public Construtor comCategoria(String categoria) {
            this.categoria = Preconditions.checkNotNull(categoria);
            return this;
        }
        
        public Construtor comAgruparCategoria(boolean agruparCategoria) {
            this.agruparCategoria = Preconditions.checkNotNull(agruparCategoria);
            return this;
        }
        
        public Construtor comLocal(int local) {
            this.local = local;
            return this;
        }
        
        public Construtor comInicio(int inicio) {
            this.inicio = inicio;
            return this;
        }
        
        public Construtor comOrdem(String idOrdem) {
            final String idOrdemLocal = Objects.firstNonNull(idOrdem, "");

            Iterable<Ordem> ordenacoes = Lists.newArrayList(Ordem.values());            
            Ordem ordem = Iterables.find(ordenacoes, new Predicate<Ordem>() {
                @Override public boolean apply(Ordem item) {
                    return item.getId().equals(idOrdemLocal);
                }
                
            }, Ordem.RELEVANCIA);
            
            return comOrdem(ordem);
        }
        
        public Construtor comOrdem(Ordem ordem) {
            this.ordem = Preconditions.checkNotNull(ordem);
            return this;
        }
        
        public Busca construir() {
            return new Busca(this);
        }
    }
    
    /**
     * Define a ordem da busca.
     */
    public static enum Ordem {
        RELEVANCIA("relevancia", "Relevância"),
        MELHOR_CLASSIFICADO("melhor-classificados", "Melhor classificados");
        
        private String id;
        private String descricao;
        
        Ordem(String id, String descricao) {
            this.id = id;
            this.descricao = descricao;
        }
        
        public String getId() {
            return id;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("termo", termo)
            .append("categoria", categoria)
            .append("agruparCategoria", agruparCategoria)
            .append("local", local)
            .append("inicio", inicio)
            .append("ordem", ordem)
            .toString();
    }
}
