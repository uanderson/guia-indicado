package com.guiaindicado.seguranca;

import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioSite extends UserDetails {

    String getNome();
    boolean isLogado();
}