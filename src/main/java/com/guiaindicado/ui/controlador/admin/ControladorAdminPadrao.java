package com.guiaindicado.ui.controlador.admin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;

import com.guiaindicado.ui.controlador.ControladorBase;

/**
 * Controlador padrão e extensão para a administração.
 * 
 * @author Uanderson Soares Gonçalves
 */
public abstract class ControladorAdminPadrao extends ControladorBase {
    
    @Value("#{environment['site.url.recurso.admin']}") String recursoUrl;
    @Value("#{environment['site.url.base']}") String siteBaseUrl;
    @Value("#{environment['site.url.base.admin']}") String baseUrl;
    @Value("#{environment['site.url.img']}") String imgUrl;
    
    /**
     * Carrega os atributos específicos e comuns para os controladores.
     * 
     * @param modelo Modeo onde serão adicionados os dados.
     */
    protected void carregarBase(Model modelo) {
        modelo.addAttribute("recursoUrl", recursoUrl);
        modelo.addAttribute("siteBaseUrl", siteBaseUrl);
        modelo.addAttribute("baseUrl", baseUrl);
        modelo.addAttribute("imgUrl", imgUrl);
        modelo.addAttribute("dataCorrente", new Date());
    }
}
