package com.guiaindicado.comando.usuario;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class ConfirmarCadastro {

    @NotBlank
    @Email
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = Objects.firstNonNull(token, "");
    }
}
