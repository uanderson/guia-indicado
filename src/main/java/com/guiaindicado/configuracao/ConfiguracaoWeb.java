package com.guiaindicado.configuracao;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

import com.guiaindicado.configuracao.suporte.ResolvedorArgumentoUsuarioSite;
import com.guiaindicado.ui.formatacao.FabricaFormatoCep;
import com.guiaindicado.ui.formatacao.FabricaFormatoTelefone;

@Configuration
@EnableWebMvc
public class ConfiguracaoWeb extends WebMvcConfigurerAdapter {

    @Autowired
    private Environment ambiente;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registro) {
        int periodoCache = Integer.valueOf(ambiente.getProperty("site.versao.cache"));

        registro.addResourceHandler("/img/**")
            .addResourceLocations(ambiente.getProperty("site.fs.imagem.recurso"))
            .setCachePeriod(periodoCache);

        registro.addResourceHandler("/" + ambiente.getProperty("site.versao") + "/**")
            .addResourceLocations("/recursos/site/", "/recursos/2nd/", "/recursos/3rd/")
            .setCachePeriod(periodoCache);
        
        registro.addResourceHandler("/" + ambiente.getProperty("site.versao") + "/admin/**")
            .addResourceLocations("/recursos/admin/", "/recursos/2nd/", "/recursos/3rd/")
            .setCachePeriod(periodoCache);
    }
    
    @Override
    public void addFormatters(FormatterRegistry registro) {
        registro.addFormatterForFieldAnnotation(new FabricaFormatoCep());
        registro.addFormatterForFieldAnnotation(new FabricaFormatoTelefone());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvedores) {
        resolvedores.add(new ResolvedorArgumentoUsuarioSite());
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver resolvedor = new SessionLocaleResolver();
        resolvedor.setDefaultLocale(new Locale("pt", "BR"));
        return resolvedor;
    }
    
    @Bean
    public TilesViewResolver tilesViewResolver() {
        return new TilesViewResolver();
    }

    @Bean
    public TilesConfigurer tilesConfigurer() {
        TilesConfigurer configurador = new TilesConfigurer();
        configurador.setDefinitions(new String[] { 
            "/WEB-INF/tiles/layout.xml", "/WEB-INF/tiles/site.xml", "/WEB-INF/tiles/admin.xml" });
        
        return configurador;
    }
    
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }
}