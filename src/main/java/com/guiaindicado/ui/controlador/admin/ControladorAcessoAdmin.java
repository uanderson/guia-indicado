package com.guiaindicado.ui.controlador.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guiaindicado.seguranca.UsuarioSite;

/**
 * Processa as requisições de login, acesso negado e erro.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorAcessoAdmin extends ControladorAdminPadrao {

    /**
     * Inicia a página para login administrativo.
     * 
     * @param usuarioSite Usuário ativo
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/login", method = RequestMethod.GET)
    public String loginAdmin(UsuarioSite usuarioSite, Model modelo) {
        if (usuarioSite.isLogado()) {
            return "redirect:/admin";
        } else {
            carregarBase(modelo);
            return "admin/login";
        }
    }
    
    /**
     * Exibe um erro de login administrativo.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/login/erro", method = RequestMethod.GET)
    public String erroLoginAdmin(Model modelo) {
        modelo.addAttribute("mensagemErro", "Senha ou e-mail inválido");
        carregarBase(modelo);
        return "admin/login";
    }
}
