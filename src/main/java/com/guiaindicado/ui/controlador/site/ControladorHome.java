package com.guiaindicado.ui.controlador.site;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaAnunciante;
import com.guiaindicado.pesquisa.PesquisaCategoria;
import com.guiaindicado.pesquisa.PesquisaDestaque;
import com.guiaindicado.pesquisa.PesquisaEmpresa;
import com.guiaindicado.ui.modelo.GrupoDestaque;

/**
 * Processa as requisições da home do site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorHome extends ControladorBuscaPadrao {

    @Autowired private PesquisaDestaque pesquisaDestaque;
    @Autowired private PesquisaCategoria pesquisaCategoria;
    @Autowired private PesquisaEmpresa pesquisaEmpresa;
    @Autowired private PesquisaAnunciante pesquisaAnunciante;
    
    /**
     * Carrega todos os dados necessário para montar a home.
     * 
     * @param modelo Onde os dados serão colocados
     * @return Nome da view
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String inicio(Model modelo) {
        Collection<GrupoDestaque> grupos = pesquisaDestaque.pesquisarGrupos();
        
        modelo.addAttribute("gruposDestaque", grupos);
        modelo.addAttribute("totalGruposDestaques", grupos.size());
        modelo.addAttribute("dezMais", pesquisaEmpresa.pesquisarDezMais());
        modelo.addAttribute("categoriasDiretas", pesquisaCategoria.pesquisarCategoriasDiretas());
        modelo.addAttribute("anunciantes", pesquisaAnunciante.pesquisarDestaque());
        
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO, PosicaoBanner.HOME, PosicaoBanner.LATERAL);
        return "home";
    }
}
