package com.guiaindicado.comando.usuario;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class CadastrarUsuario {

    @NotBlank(message = "{cadastrarUsuario.nome.obrigatorio}")
    @Size(max = 70, message = "{cadastrarUsuario.nome.tamanhoMaximo}")
    private String nome;
    
    @NotBlank(message = "{cadastroUsuario.email.obrigatorio}")
    @Size(max = 255, message = "{cadastroUsuario.email.tamanhoMaximo}")
    @Email(message = "{cadastroUsuario.email.invalido}")
    private String email;
    private Integer cidade;
    
    @NotBlank(message = "{cadastroUsuario.senha.obrigatorio}")
    @Size(min = 4, message = "{cadastroUsuario.senha.tamanhoMinimo}")
    private String senha;
    
    @AssertTrue(message = "{cadastroUsuario.termoAceito.verdadeiro}")
    private Boolean termoAceito;
    private Boolean receberEmail;

    public CadastrarUsuario() {
        nome = "";
        email = "";
        cidade = 0;
        senha = "";
        termoAceito = false;
        receberEmail = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = Objects.firstNonNull(nome, "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.firstNonNull(email, "");
    }

    public Integer getCidade() {
        return cidade;
    }

    public void setCidade(Integer cidade) {
        this.cidade = Objects.firstNonNull(cidade, 0);
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = Objects.firstNonNull(senha, "");
    }

    public Boolean getTermoAceito() {
        return termoAceito;
    }

    public void setTermoAceito(Boolean termoAceito) {
        this.termoAceito = Objects.firstNonNull(termoAceito, false);
    }
    
    public Boolean getReceberEmail() {
        return receberEmail;
    }

    public void setReceberEmail(Boolean receberEmail) {
        this.receberEmail = Objects.firstNonNull(receberEmail, false);
    }
}
