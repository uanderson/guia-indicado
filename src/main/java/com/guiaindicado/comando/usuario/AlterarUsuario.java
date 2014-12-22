package com.guiaindicado.comando.usuario;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class AlterarUsuario {

    @NotBlank(message = "{alterarUsuario.nome.obrigatorio}")
    @Size(max = 70, message = "{alterarUsuario.nome.tamanhoMaximo}")
    private String nome;
    private Integer cidade;
    private Boolean receberEmail;

    public AlterarUsuario() {
        nome = "";
        cidade = 0;
        receberEmail = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.firstNonNull(nome, "");
    }

    public Integer getCidade() {
        return cidade;
    }

    public void setCidade(Integer cidade) {
        this.cidade = Objects.firstNonNull(cidade, 0);
    }

    public Boolean getReceberEmail() {
        return receberEmail;
    }

    public void setReceberEmail(Boolean receberEmail) {
        this.receberEmail = Objects.firstNonNull(receberEmail, false);
    }
}
