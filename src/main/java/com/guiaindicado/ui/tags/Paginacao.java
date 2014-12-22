package com.guiaindicado.ui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.guiaindicado.suporte.ResultadoPaginado;

/**
 * Tag responsável por gerar o HTML para a paginação.
 * 
 * @author Uanderson Soares Gonçalves
 */
@SuppressWarnings("serial")
public class Paginacao extends TagSupport {

    /**
     * Define o número padrão de páginas.
     */
    private static final int NUMERO_PAGINAS = 6;

    private String url;
    private ResultadoPaginado<?> resultado;
    
    /**
     * Renderiza efetivamente o HTML para a página.
     * 
     * @see TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        InfoPaginacao info = new InfoPaginacao(resultado);
        
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"pagination pagination-centered\"><ul>");
        
        renderizarAnterior(info, html);
        renderizarPaginas(info, html);
        renderizarProxima(info, html);
        
        html.append("</ul></div>");
        
        try {
            pageContext.getOut().write(html.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return SKIP_BODY;
    }
    
    /**
     * Renderiza a seta de página anterior.
     * 
     * @param info Dados da paginação
     * @param html String builder para armazenar o html gerado
     */
    private void renderizarAnterior(InfoPaginacao info, StringBuilder html) {
        if (info.getPaginaCorrente() == 0) {
            html.append("<li class=\"disabled\"><a href=\"#\">«</a></li>");
        } else {
            html.append("<li><a href=\"");
            html.append(url);
            html.append("/?inicio=");
            html.append(info.getAnterior());
            html.append("\">«</a></li>");
        }
    }
    
    /**
     * Renderiza a parte central da paginação.
     * 
     * @param info Dados da paginação
     * @param html String builder para armazenar o html gerado
     */
    private void renderizarPaginas(InfoPaginacao info, StringBuilder html) {
        
        for (int indice = info.getInicio(); indice <= info.getFim(); indice++) {
            if (info.getPaginaCorrente() == indice) {
                html.append("<li class=\"disabled\"><a href=\"#\">");
                html.append(indice + 1);
                html.append("</a></li>");
            } else {
                html.append("<li><a href=\"");
                html.append(url);
                html.append("/?inicio=");
                html.append(indice * info.getTamanho());
                html.append("\">");
                html.append(indice + 1); 
                html.append("</a></li>");
            }
        }
    }

    /**
     * Renderiza a seta de próxima página.
     * 
     * @param info Dados da paginação
     * @param html String builder para armazenar o html gerado
     */
    private void renderizarProxima(InfoPaginacao info, StringBuilder html) {
        if (info.getPaginaCorrente() == (info.getTotalPaginas() - 1)) {
            html.append("<li class=\"disabled\"><a href=\"#\">»</a></li>");
        } else {
            html.append("<li><a class=\"proxima\" href=\"");
            html.append(url);
            html.append("/?inicio=");
            html.append(info.getProxima());
            html.append("\">»</a></li>");
        }
    }
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ResultadoPaginado<?> getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoPaginado<?> resultado) {
        this.resultado = resultado;
    }
    
    /**
     * Container para armazenar os dados da paginação. 
     */
    private static class InfoPaginacao {
        
        private int totalPaginas;
        private int tamanho;
        private int paginaCorrente;
        private int inicio;
        private int fim;
        
        InfoPaginacao(ResultadoPaginado<?> resultado) {
            this.totalPaginas = resultado.getTotalPaginas();
            this.tamanho = resultado.getPagina().getTamanho();
            this.paginaCorrente = resultado.getPaginaCorrente();
            this.inicio = paginaCorrente - (NUMERO_PAGINAS / 2);
            this.fim = paginaCorrente + (NUMERO_PAGINAS / 2);
            
            if (this.inicio < 0) {
                this.inicio = 0;
                this.fim += (NUMERO_PAGINAS / 2);
            }
            
            if (this.fim > (totalPaginas - 1)) {
                this.fim = totalPaginas - 1;
                this.inicio -= (NUMERO_PAGINAS / 2);
            }
            
            if (this.inicio < 0) {
                this.inicio = 0;
            }
            
            if (this.fim < 0) {
                this.fim = 0;
            }
        }

        public int getTotalPaginas() {
            return totalPaginas;
        }

        public int getTamanho() {
            return tamanho;
        }

        public int getPaginaCorrente() {
            return paginaCorrente;
        }

        public int getInicio() {
            return inicio;
        }

        public int getFim() {
            return fim;
        }
        
        public int getAnterior() {
            return (paginaCorrente * tamanho) - tamanho;
        }
        
        public int getProxima() {
            return (paginaCorrente * tamanho) + tamanho;
        }
    }
}
