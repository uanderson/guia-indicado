package com.guiaindicado.comando.usuario;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class ReiniciarSenha {

    private String token;
    
    @NotBlank(message = "{reiniciarSenha.senha.obrigatorio}")
    @Size(min = 4, message = "{reiniciarSenha.senha.tamanhoMinimo}")
    private String senha;
    
    @NotBlank(message = "{reiniciarSenha.confirmacao.obrigatorio}")
    private String confirmacao;

    public ReiniciarSenha() {
        token = "";
        senha = "";
        confirmacao = "";
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = Objects.firstNonNull(token, "");
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Objects.firstNonNull(senha, "");
    }

    public String getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(String confirmacao) {
        this.confirmacao = Objects.firstNonNull(confirmacao, "");
    }
}
