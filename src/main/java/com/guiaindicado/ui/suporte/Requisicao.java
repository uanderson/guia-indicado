package com.guiaindicado.ui.suporte;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Métodos utilizários para a requisição.
 * 
 * @author Uanderson Soares Gonçalves
 */
public final class Requisicao {

    /**
     * Verifica se a requisição é ajax baseado no header que as ferramentas ajax enviam.
     * 
     * @return true se for ajax, false caso contrário
     */
    public static boolean requisicaoAjax() {
        return requisicaoAjax(get());
    }
    
    /**
     * Verifica se a requisição é ajax baseado no header que as ferramentas ajax enviam.
     * 
     * @param requisicao Requisição corrente
     * @return true se for ajax, false caso contrário
     */
    public static boolean requisicaoAjax(HttpServletRequest requisicao) {
        return "XMLHttpRequest".equals(requisicao.getHeader("X-Requested-With"));
    }

    /**
     * Obtém a requisição corrente através do contexto do Spring.
     * 
     * @return Requisição corrente
     */
    public static HttpServletRequest get() {
        RequestAttributes atributosRequest = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes atributosServlet = (ServletRequestAttributes) atributosRequest;

        return atributosServlet.getRequest();
    }

    private Requisicao() {
    }
}
