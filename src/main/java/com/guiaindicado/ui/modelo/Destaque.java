package com.guiaindicado.ui.modelo;

import java.util.Date;

import com.guiaindicado.dominio.geral.TipoMedia;

public class Destaque {

    private Integer id;
    private Integer empresa;
    private String nomeEmpresa;
    private String referenciaEmpresa;
    private String imagem;
    private String titulo;
    private String texto;
    private Boolean vertical;
    private String referencia;
    private Date dataHoraInicio;
    private Date dataHoraTermino;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = empresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getReferenciaEmpresa() {
        return referenciaEmpresa;
    }

    public void setReferenciaEmpresa(String referenciaEmpresa) {
        this.referenciaEmpresa = referenciaEmpresa;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Boolean getVertical() {
        return vertical;
    }

    public void setVertical(Boolean vertical) {
        this.vertical = vertical;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getDataHoraInicio() {
        return dataHoraInicio;
    }

    public void setDataHoraInicio(Date dataHoraInicio) {
        this.dataHoraInicio = dataHoraInicio;
    }

    public Date getDataHoraTermino() {
        return dataHoraTermino;
    }

    public void setDataHoraTermino(Date dataHoraTermino) {
        this.dataHoraTermino = dataHoraTermino;
    }

    public String getImagemRelativa() {
        return imagem + "." + TipoMedia.JPEG.getExtensao();
    }
}
