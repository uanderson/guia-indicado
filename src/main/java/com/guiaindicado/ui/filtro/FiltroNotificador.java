package com.guiaindicado.ui.filtro;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.guiaindicado.notificacao.Notificador;

/**
 * Filtro que finaliza o processamento da classe {@link Notificador} que utiliza
 * {@link ThreadLocal} para manter as notificações para a requisição corrente.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class FiltroNotificador implements Filter {

    /**
     * @see Filter#init(FilterConfig)
     */
    @Override
    public void init(FilterConfig configuracao) throws ServletException {
    }
    
    /**
     * @see Filter#destroy()
     */
    @Override
    public void destroy() {
    }

    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    @Override
    public void doFilter(ServletRequest requisicao, ServletResponse resposta, FilterChain chain)
        throws IOException, ServletException {
        
        chain.doFilter(requisicao, resposta);
        Notificador.liberar();
    }
}
