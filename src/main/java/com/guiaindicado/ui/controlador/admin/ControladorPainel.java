package com.guiaindicado.ui.controlador.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Controlador da home administrativa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorPainel extends ControladorAdminPadrao {

    /**
     * Inicia a página da home administrativa.
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String inicio(Model modelo) {
        carregarBase(modelo);
        return "admin";
    }
}
