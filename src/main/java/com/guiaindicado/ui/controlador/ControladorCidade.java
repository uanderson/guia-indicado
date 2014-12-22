package com.guiaindicado.ui.controlador;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.pesquisa.PesquisaCidade;
import com.guiaindicado.ui.controlador.site.ControladorSitePadrao;
import com.guiaindicado.ui.modelo.Cidade;

/**
 * Processa as requisições referentes a cidade.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorCidade extends ControladorSitePadrao {

    @Autowired private PesquisaCidade pesquisaCidade;
    
    /**
     * Pesquisa a cidade por nome.
     * 
     * @param nome Nome da cidade
     * @return Dados das cidades
     */
    @RequestMapping(value = "/cidades/pesquisa.json", method = RequestMethod.GET)
    public @ResponseBody Collection<Cidade> pesquisar(
            @RequestParam(value = "termo", required = false) String nome) {
        return pesquisaCidade.pesquisarPorNome(nome);
    }
}
