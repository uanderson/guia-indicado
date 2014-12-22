package com.guiaindicado.ui.modelo;

import java.text.MessageFormat;

public class ItemBusca {

    private Integer id;
    private String nome;
    private String tipo;
    private String referencia;
    private String endereco;
    private String numero;
    private String bairro;
    private String telefone;
    private Integer avaliacoes;
    private Integer mediaAvaliacao;
    private String imagemPrincipal;

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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Integer getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Integer avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Integer getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public void setMediaAvaliacao(Integer mediaAvaliacao) {
        this.mediaAvaliacao = mediaAvaliacao;
    }

    public String getImagemPrincipal() {
        return imagemPrincipal;
    }

    public void setImagemPrincipal(String imagemPrincipal) {
        this.imagemPrincipal = imagemPrincipal;
    }
    
    public String getImagemRelativaBusca() {
        if (imagemPrincipal == null || imagemPrincipal.isEmpty()) {
            return "sem-imgb.jpg";
        }
        
        return MessageFormat.format("{0}/{1,number,#}/{2}{3}", tipo, id, imagemPrincipal, "-b.jpg");
    }
    
    public String getUrlDetalheItem() {
        return MessageFormat.format("{0}/{1}/{2,number,#}", tipo, referencia, id);
    }
}
