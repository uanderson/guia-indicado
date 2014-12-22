package com.guiaindicado.ui.controlador.site;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.google.common.collect.Lists;
import com.guiaindicado.pesquisa.PesquisaCidade;
import com.guiaindicado.ui.modelo.Cidade;

/**
 * Controlador de busca base para os controladores que tem função de busca.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class ControladorBuscaPadrao extends ControladorSitePadrao {

    @Autowired private PesquisaCidade pesquisaCidade;
    
    /**
     * Obtém a cidade da busca padrão através do cookie ULOCAL. Quando o cookie não existe,
     * o padrão é cair na cidade de Umuarama.
     * 
     * @param resposta Resposta para criar o cookie
     * @param cookieLocal Local da busca
     * @return Cidade
     */
    @ModelAttribute("cidadeBusca")
    public Cidade getCidadeBusca(HttpServletResponse resposta,
        @CookieValue(value = "ULOCAL", required = false) String cookieLocal) {
        
        int local = NumberUtils.toInt(cookieLocal);
        
        if (local == 0) {
            local = 412810;
            criarCookieLocal(local, resposta);
        }
        
        return pesquisaCidade.pesquisarPorId(local).orNull();
    }
    
    /**
     * Obtém a lista de cidades indicadas.
     * 
     * @return Cidades indicadas
     */
    @ModelAttribute("cidadesIndicadas")
    public Collection<Cidade> getCidadesIndicadas() {
        return Lists.newArrayList();
    }
    
    /**
     * Cria um cookie com o local padrão da busca.
     * 
     * @param local Local padrão da busca
     * @param resposta Resposta onde o cookie será criado
     */
    protected void criarCookieLocal(int local, HttpServletResponse resposta) {
        Cookie cookieLocal = new Cookie("ULOCAL", String.valueOf(local));
        cookieLocal.setMaxAge(86400 * 365 * 10);
        resposta.addCookie(cookieLocal);
    }
}
