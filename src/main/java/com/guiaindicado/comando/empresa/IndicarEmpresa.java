package com.guiaindicado.comando.empresa;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.google.common.base.Objects;
import com.guiaindicado.ui.formatacao.FormatoCep;
import com.guiaindicado.ui.formatacao.FormatoTelefone;
import com.guiaindicado.validacao.Cep;
import com.guiaindicado.validacao.Telefone;

public class IndicarEmpresa {

    private Integer id;
    
    @NotBlank(message = "{indicarEmpresa.nome.obrigatorio}")
    @Size(max = 100, message = "{indicarEmpresa.nome.tamanhoMaximo}")
    private String nome;

    @Size(max = 1000, message = "{indicarEmpresa.descricao.tamanhoMaximo}")
    private String descricao;
    private Integer categoria;
    
    @NotBlank(message = "{indicarEmpresa.categoria.obrigado}")
    private String nomeCategoria;
    
    @Size(max = 1000, message = "{indicarEmpresa.tags.tamanhoMaximo}")
    private String tags;
    
    @FormatoCep
    @Cep(message = "{indicarEmpresa.cep.invalido}")
    private String cep;
    
    @NotEmpty(message = "{indicarEmpresa.endereco.obrigatorio}")
    @Size(max = 70, message = "{indicarEmpresa.endereco.tamanhoMaximo}")
    private String endereco;
    
    @Size(max = 10, message = "{indicarEmpresa.numero.tamanhoMaximo}")
    private String numero;
    
    @Size(max = 70, message = "{indicarEmpresa.bairro.tamanhoMaximo}")
    private String bairro;

    @Min(value = 1, message = "{indicarEmpresa.cidade.obrigatorio}")
    private Integer cidade;

    @FormatoTelefone
    @NotBlank(message = "{indicarEmpresa.telefone.obrigatorio}")
    @Telefone(message = "{indicarEmpresa.telefone.invalido}")
    private String telefone;

    @FormatoTelefone
    @Telefone(message = "{indicarEmpresa.celular.invalido}")
    private String celular;
    
    @Size(max = 255, message = "{indicarEmpresa.email.tamanhoMaximo}")
    @Email(message = "{indicarEmpresa.email.invalido}")
    private String email;
    
    @Size(max = 255, message = "{indicarEmpresa.site.tamanhoMaximo}")
    @URL(message = "{indicarEmpresa.site.invalido}")
    private String site;

    public IndicarEmpresa() {
        id = 0;
        nome = "";
        descricao = "";
        tags = "";
        categoria = 0;
        cep = "";
        endereco = "";
        numero = "";
        bairro = "";
        cidade = 0;
        telefone = "";
        celular = "";
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = Objects.firstNonNull(descricao, "");
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = Objects.firstNonNull(categoria, 0);
    }
    
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = Objects.firstNonNull(nomeCategoria, "");
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = Objects.firstNonNull(tags, "");
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = Objects.firstNonNull(cep, "");
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = Objects.firstNonNull(endereco, "");
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = Objects.firstNonNull(numero, "");
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = Objects.firstNonNull(bairro, "");
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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = Objects.firstNonNull(celular, "");
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
}
