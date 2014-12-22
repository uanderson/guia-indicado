package com.guiaindicado.seguranca;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public final class Seguranca {

    public static String codificarSenha(String senha, String salto) {
        return new ShaPasswordEncoder(256).encodePassword(senha, salto);
    }
    
    private Seguranca() {
    }
}
