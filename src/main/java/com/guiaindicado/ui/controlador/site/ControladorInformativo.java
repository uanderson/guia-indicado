package com.guiaindicado.ui.controlador.site;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guiaindicado.ui.suporte.Requisicao;

/**
 * Processa as requisições pertinentes a informação das empresas, termos legais e de uso.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorInformativo extends ControladorSitePadrao {
    
    /**
     * Inicia a tela de informação da empresa.
     * 
     * @param requisicao Requisição corrente
     * @return Nome da view
     */
    @RequestMapping(value = "/empresa", method = RequestMethod.GET)
    public String sobreEmpresa(HttpServletRequest requisicao, Model modelo) {
        if (!Requisicao.requisicaoAjax()) {
            return "redirect:/";
        }
        
        carregarBase(modelo);
        return "empresa";
    }
    
    /**
     * Inicia a tela de termos de uso e privacidade.
     * 
     * @param requisicao Requisição corrente
     * @return Nome da view
     */
    @RequestMapping(value = "/termo", method = RequestMethod.GET)
    public String termosUso(HttpServletRequest requisicao, Model modelo) {
        if (!Requisicao.requisicaoAjax()) {
            return "redirect:/";
        }
        
        carregarBase(modelo);
        return "termo";
    }
}
