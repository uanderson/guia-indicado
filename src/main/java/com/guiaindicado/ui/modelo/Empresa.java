package com.guiaindicado.ui.modelo;

import java.text.MessageFormat;

import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.annotation.NumberFormat.Style;

import com.guiaindicado.ui.formatacao.FormatoCep;
import com.guiaindicado.ui.formatacao.FormatoTelefone;

public class Empresa {

    private Integer id;
    private String nome;
    private Integer categoria;
    private String nomeCategoria;
    private String referenciaCategoria;
    private String descricao;
    private String tags;
    private String referencia;
    private String referenciaImagem;

    @FormatoCep
    private String cep;
    private String endereco;
    private String numero;
    private String bairro;
    private Integer cidade;
    private String nomeCidade;
    private String nomeEstado;
    private String siglaEstado;

    @FormatoTelefone
    private String telefone;

    @FormatoTelefone
    private String celular;
    private String email;
    private String site;

    @NumberFormat(style = Style.NUMBER, pattern = "0.00######")
    private Double latitude;

    @NumberFormat(style = Style.NUMBER, pattern = "0.00######")
    private Double longitude;
    private Integer status;

    private String imagemPrincipal;
    private Integer mediaAvaliacao;
    private Integer avaliacoes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getCategoria() {
        return categoria;
    }

    public void setCategoria(Integer categoria) {
        this.categoria = categoria;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getReferenciaCategoria() {
        return referenciaCategoria;
    }

    public void setReferenciaCategoria(String referenciaCategoria) {
        this.referenciaCategoria = referenciaCategoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getReferenciaImagem() {
        return referenciaImagem;
    }

    public void setReferenciaImagem(String referenciaImagem) {
        this.referenciaImagem = referenciaImagem;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public Integer getCidade() {
        return cidade;
    }

    public void setCidade(Integer cidade) {
        this.cidade = cidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    public String getNomeEstado() {
        return nomeEstado;
    }

    public void setNomeEstado(String nomeEstado) {
        this.nomeEstado = nomeEstado;
    }

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }

    public Integer getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Integer mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public Integer getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Integer avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public String getImagemRelativa() {
        if (imagemPrincipal == null || imagemPrincipal.isEmpty()) {
            return "sem-imgm.jpg";
        }

        return MessageFormat.format("{0}/{1,number,#}/{2}{3}", "empresa", id,
            referenciaImagem, "-m.jpg");
    }
}
