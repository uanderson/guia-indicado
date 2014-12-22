package com.guiaindicado.suporte;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

/**
 * Mantém referencia a um resultado paginado.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class ResultadoPaginado<T> {

    private int total;
    private Pagina pagina;
    private Collection<T> colecao = Sets.newLinkedHashSet();
    private int totalPaginas;
    private int paginaCorrente;
    
    /**
     * Cria um novo resultado paginado.
     * 
     * @param pagina Requisição original de paginação
     * @param colecao Coleção com resultado
     * @param total Total de registros na base
     */
    private ResultadoPaginado(Pagina pagina, Collection<T> colecao, int total) {
        this(pagina, colecao, total, 0);
    }
    
    /**
     * Cria um novo resultado paginado.
     * 
     * @param pagina Requisição original de paginação
     * @param colecao Coleção com resultado
     * @param total Total de registros na base
     */
    private ResultadoPaginado(Pagina pagina, Collection<T> colecao, int total, int totalReduzido) {
        this.total = total;
        this.pagina = pagina;
        this.colecao.addAll(colecao);
        
        int totalLocal = (totalReduzido == 0) ? total : totalReduzido;
        int inicio = this.pagina.getInicio();
        
        if (total < totalLocal) {
            totalLocal = total;
        }
        
        this.totalPaginas = (int) Math.ceil((double) totalLocal / pagina.getTamanho());
        
        if (this.pagina.getInicio() > (total - 1)) {
            inicio = (total - 1);
        }
        
        this.paginaCorrente = (inicio / pagina.getTamanho());
    }
    
    /**
     * @see #ResultadoPaginado(Pagina, Collection, int)
     * @param pagina Página
     * @param colecao Coleção
     * @param total Total de registros
     * @return
     */
    public static <T> ResultadoPaginado<T> criar(Pagina pagina, Collection<T> colecao, int total) {
        return new ResultadoPaginado<T>(pagina, colecao, total);
    }
    
    /**
     * @see #ResultadoPaginado(Pagina, Collection, int, int)
     * @param pagina Página
     * @param colecao Coleção
     * @param total Total de registros
     * @param totalReduzido Calculo diferenciado do número de páginas
     * @return
     */
    public static <T> ResultadoPaginado<T> criar(Pagina pagina, Collection<T> colecao, 
            int total, int totalReduzido) {
        
        return new ResultadoPaginado<T>(pagina, colecao, total, totalReduzido);
    }

    public int getTotal() {
        return total;
    }

    public Pagina getPagina() {
        return pagina;
    }

    public Collection<T> getColecao() {
        return ImmutableSet.copyOf(colecao);
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public int getPaginaCorrente() {
        return paginaCorrente;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("total", total)
            .append("pagina", pagina)
            .append("totalPaginas", totalPaginas)
            .append("paginaCorrente", paginaCorrente)
            .toString();
    }
}
