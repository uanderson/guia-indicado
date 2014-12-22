package com.guiaindicado.ui.controlador.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Processa as requisições para saber o status da aplicação.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorStatus {

    /**
     * Apenas responde com código 200 caso a aplicação esteja ativa.
     */
    @RequestMapping(value = "/status", method = RequestMethod.GET)
    public @ResponseBody void status() {
    }
}
