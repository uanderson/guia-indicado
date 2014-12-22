package com.guiaindicado.comando.usuario;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class AlterarSenha {

    @NotBlank(message = "{alterarSenha.senhaAtual.obrigatorio}")
    private String senhaAtual;
    
    @NotBlank(message = "{alterarSenha.senha.obrigatorio}")
    @Size(min = 4, message = "{alterarSenha.senha.tamanhoMinimo}")
    private String senha;
    
    @NotBlank(message = "{alterarSenha.confirmacao.obrigatorio}")
    private String confirmacao;

    public AlterarSenha() {
        senhaAtual = "";
        senha = "";
        confirmacao = "";
    }
    
    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = Objects.firstNonNull(senhaAtual, "");
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
