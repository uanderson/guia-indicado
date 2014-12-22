package com.guiaindicado.suporte;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Classe utilizada nos serviços para identificar uma listagem paginada.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class Pagina {

    public static final int INICIO_PADRAO = 0;
    public static final int TAMANHO_PADRAO = 10;
    
    private int inicio;
    private int tamanho;

    /**
     * Cria uma página com tamanho padrão.
     * 
     * @param inicio Número da página
     */
    private Pagina(int inicio) {
        this(inicio, TAMANHO_PADRAO);
    }
    
    /**
     * Cria uma página com inicio e tamanho de página.
     * 
     * @param inicio Número da página
     * @param tamanho Tamanho da página
     */
    private Pagina(int inicio, int tamanho) {
        this.inicio = (inicio < -1) ? INICIO_PADRAO : inicio;
        this.tamanho = (tamanho < -1) ? TAMANHO_PADRAO : tamanho;
    }

    /**
     * @see #Pagina(int)
     */
    public static Pagina criar(int inicio) {
        return new Pagina(inicio);
    }
    
    /**
     * @see #Pagina(int, int)
     */
    public static Pagina criar(int inicio, int tamanho) {
        return new Pagina(inicio, tamanho);
    }

    /**
     * Define o inicio baseado no total de elementos. Se o inicio for maior que o total, ajusta o
     * inicio para ficar igual ao total, caso contrário mantém o inicio.
     * 
     * @param total Total de elementos
     */
    public void definirInicio(int total) {
        inicio = (inicio >= total) ? (total - 1) : inicio;
        
        if (inicio < 0) {
            inicio = 0;
        }
    }
    
    public int getInicio() {
        return inicio;
    }

    public int getTamanho() {
        return tamanho;
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("inicio", inicio)
            .append("tamanho", tamanho)
            .toString();
    }
}
