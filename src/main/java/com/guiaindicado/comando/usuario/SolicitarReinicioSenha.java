package com.guiaindicado.comando.usuario;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class SolicitarReinicioSenha {

    @NotBlank(message = "{solicitarReinicioSenha.email.obrigatorio}")
    @Size(max = 255, message = "{solicitarReinicioSenha.email.tamanhoMaximo}")
    @Email(message = "{solicitarReinicioSenha.email.invalido}")
    private String email;

    public SolicitarReinicioSenha() {
        email = "";
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.firstNonNull(email, "");
    }
}
