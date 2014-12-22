package com.guiaindicado.comando.geral;

import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;
import com.guiaindicado.ui.formatacao.FormatoTelefone;
import com.guiaindicado.validacao.Telefone;

public class Contatar {
    
    @NotBlank(message = "{contatar.nome.obrigatorio}")
    @Size(max = 70, message = "{contatar.nome.tamanhoMaximo}")
    private String nome;

    @NotBlank(message = "{contatar.email.obrigatorio}")
    @Size(max = 255, message = "{contatar.email.tamanhoMaximo}")
    @Email(message = "{contatar.email.invalido}")
    private String email;
    
    @Telefone(message = "{contatar.telefone.invalido}")
    @FormatoTelefone
    private String telefone;
    
    @NotBlank(message = "{contatar.mensagem.obrigatorio}")
    @Size(max = 600, message = "{contatar.mensagem.tamanhoMaximo}")
    private String mensagem;

    public Contatar() {
        nome = "";
        email = "";
        telefone = "";
        mensagem = "";
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = Objects.firstNonNull(telefone, "");
    }
    
    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = Objects.firstNonNull(StringEscapeUtils.escapeHtml4(mensagem), "");
    }
}
