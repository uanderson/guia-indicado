package com.guiaindicado.ui.controlador.site;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaBanner;
import com.guiaindicado.ui.controlador.ControladorBase;

/**
 * Controlador base do site.
 * 
 * @author Uanderson Soares Gonçalves
 */
public abstract class ControladorSitePadrao extends ControladorBase {

    @Autowired private PesquisaBanner pesquisaBanner;
    
    @Value("#{environment['site.url.recurso']}") String recursoUrl;
    @Value("#{environment['site.url.base']}") String baseUrl;
    @Value("#{environment['site.url.img']}") String imgUrl;
    
    /**
     * Carrega os atributos específicos e comuns para os controladores.
     * 
     * @param modelo Modeo onde serão adicionados os dados.
     */
    protected void carregarBase(Model modelo) {
        modelo.addAttribute("recursoUrl", recursoUrl);
        modelo.addAttribute("baseUrl", baseUrl);
        modelo.addAttribute("imgUrl", imgUrl);
        modelo.addAttribute("dataCorrente", new Date());
    }
    
    /**
     * Operação que realiza a carga dos banners da aplicação. Esse método é chamado pelas
     * subclasses e elas decidem quando é necessário carregar cada banner. Para chamadas
     * ajax, não há necessidade.
     * 
     * @param modelo Onde os banners serão colocados
     */
    protected void carregarBanners(Model modelo, PosicaoBanner... posicoes) {
        for (PosicaoBanner posicao : posicoes) {
            modelo.addAttribute("BANNER_" + posicao.name(), pesquisaBanner
                .pesquisarPorPosicao(posicao));
        }
    }
}
