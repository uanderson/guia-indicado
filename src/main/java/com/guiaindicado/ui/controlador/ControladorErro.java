package com.guiaindicado.ui.controlador;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.ui.controlador.site.ControladorBuscaPadrao;

/**
 * Processa as páginas de erro da aplicação.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorErro extends ControladorBuscaPadrao {

    /**
     * Processa a página de erro 404.
     * 
     * @return Nome da view
     */
    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public String erro404(Model modelo) {
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO);
        return "404";
    }
}
