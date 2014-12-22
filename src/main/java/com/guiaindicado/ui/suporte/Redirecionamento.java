package com.guiaindicado.ui.suporte;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Codifica as URLs para redirecionamento da aplicação.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class Redirecionamento {

    @Value("#{environment['site.url.base']}") String siteBaseUrl;
    @Value("#{environment['site.url.base.admin']}") String baseUrl;
    
    /**
     * Codifica a URL para redirecionamento do site.
     * 
     * @param urlRelativa URL relativa para redirecionamento
     * @return URL completa
     */
    public String codificarSite(String urlRelativa) {
        return siteBaseUrl + urlRelativa;
    }
    
    /**
     * Codifica a URL para redirecionamento na área administrativa.
     * 
     * @param urlRelativa URL relativa para redirecionamento
     * @return URL completa
     */
    public String codificarAdmin(String urlRelativa) {
        return baseUrl + urlRelativa;
    }
}
