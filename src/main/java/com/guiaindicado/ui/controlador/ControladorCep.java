package com.guiaindicado.ui.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.guiaindicado.pesquisa.PesquisaCep;
import com.guiaindicado.ui.controlador.site.ControladorSitePadrao;
import com.guiaindicado.ui.modelo.Cep;

/**
 * Processa as requisições referentes a CEP.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorCep extends ControladorSitePadrao {

    @Autowired private PesquisaCep pesquisaCep;
    
    /**
     * Pesquisa o CEP pelo número.
     * 
     * @param nome Número do CEP
     * @return Dados do CEP
     */
    @RequestMapping(value = "/ceps/pesquisa.json", method = RequestMethod.GET)
    public @ResponseBody Cep pesquisar(
            @RequestParam(value = "cep", required = false) String cep) {
        String numero = Strings.nullToEmpty(cep).replaceAll("[^0-9]", "");
        return pesquisaCep.pesquisarPorCep(numero).orNull();
    }
}
