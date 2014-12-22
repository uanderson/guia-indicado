package com.guiaindicado.comando.anunciante;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import com.google.common.base.Objects;
import com.guiaindicado.ui.formatacao.FormatoTelefone;
import com.guiaindicado.validacao.Telefone;

public class SalvarAnunciante {

    private Integer id;
    
    @NotBlank(message = "{salvarAnunciante.nome.obrigatorio}")
    @Size(max = 100, message = "{salvarAnunciante.nome.tamanhoMaximo}")
    private String nome;

    @Min(value = 1, message = "{salvarAnunciante.cidade.obrigatorio}")
    private Integer cidade;

    @NotBlank(message = "{salvarAnunciante.telefone.obrigatorio}")
    @Telefone(message = "{salvarAnunciante.telefone.invalido}")
    @FormatoTelefone
    private String telefone;

    @Size(max = 255, message = "{salvarAnunciante.email.tamanhoMaximo}")
    @Email(message = "{salvarAnunciante.email.invalido}")
    private String email;
    
    @Size(max = 255, message = "{salvarAnunciante.site.tamanhoMaximo}")
    @URL(message = "{salvarAnunciante.site.invalido}")
    private String site;
    private String imagem;

    public SalvarAnunciante() {
        id = 0;
        nome = "";
        cidade = 0;
        telefone = "";
        email = "";
        site = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Objects.firstNonNull(id, 0);
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = Objects.firstNonNull(telefone, "");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = Objects.firstNonNull(email, "");
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = Objects.firstNonNull(site, "");
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
