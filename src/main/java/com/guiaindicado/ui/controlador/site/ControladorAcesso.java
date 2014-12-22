package com.guiaindicado.ui.controlador.site;

import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.seguranca.UsuarioSite;

/**
 * Processa as requisições de login, acesso negado e erro.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorAcesso extends ControladorBuscaPadrao {

    /**
     * Inicia a página de login caso o usuário não esteja logado.
     * 
     * @param usuarioSite Usuário ativo
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(UsuarioSite usuarioSite, Model modelo) {
        if (usuarioSite.isLogado()) {
            return "redirect:/";
        } else {
            carregarBase(modelo);
            carregarBanners(modelo, PosicaoBanner.TOPO);
            return "login";
        }
    }
    
    /**
     * Exibe uma mensagem de erro para o usuário.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/login/erro", method = RequestMethod.GET)
    public String erroLogin(HttpSession sessao, Model modelo) {
        Exception excecaoLogin = (Exception) sessao.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        
        if (excecaoLogin instanceof DisabledException) {
            modelo.addAttribute("mensagemErro", "Seu cadastro ainda não foi confirmado");
        } else {
            modelo.addAttribute("mensagemErro", "Senha ou e-mail inválido");
        }
        
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO);
        return "login";
    }
}
