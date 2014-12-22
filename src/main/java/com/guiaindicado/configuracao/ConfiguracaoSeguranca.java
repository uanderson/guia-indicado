package com.guiaindicado.configuracao;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.web.authentication.ExceptionMappingAuthenticationFailureHandler;

import com.google.common.collect.Maps;

@Configuration
@ImportResource("classpath:configuracaoSeguranca.xml")
public class ConfiguracaoSeguranca {
    
    @Bean
    public ExceptionMappingAuthenticationFailureHandler processadorFalhaAutenticacao() {
        ExceptionMappingAuthenticationFailureHandler processador = 
            new ExceptionMappingAuthenticationFailureHandler();
        
        Map<String, String> excecoes = Maps.newHashMap();
        excecoes.put(BadCredentialsException.class.getName(), "/login/invalido");
        excecoes.put(DisabledException.class.getName(), "/login/desabilitado");
        
        processador.setExceptionMappings(excecoes);
        
        return processador;
    }
}