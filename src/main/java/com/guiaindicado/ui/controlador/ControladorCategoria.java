package com.guiaindicado.ui.controlador;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.pesquisa.PesquisaCategoria;
import com.guiaindicado.ui.controlador.site.ControladorSitePadrao;
import com.guiaindicado.ui.modelo.Categoria;

/**
 * Processa requisições referentes as categorias.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorCategoria extends ControladorSitePadrao {

    @Autowired private PesquisaCategoria pesquisaCategoria;
    
    /**
     * Pesquisa as categorias por nome.
     * 
     * @param nome Nome da categoria
     * @return Coleção de categorias
     */
    @RequestMapping(value = "/categorias/pesquisa.json", method = RequestMethod.GET)
    public @ResponseBody Collection<Categoria> pesquisar(
            @RequestParam(value = "termo", required = false) String nome) {
        return pesquisaCategoria.pesquisarPorNome(nome);
    }
}
