package com.guiaindicado.ui.modelo;

import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;

public class Banner {

    private Integer id;
    private Integer anunciante;
    private String nomeAnunciante;
    private String url;
    private String imagem;
    private PosicaoBanner posicao;
    private TipoMedia tipoMedia;
    private Boolean ativo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Integer anunciante) {
        this.anunciante = anunciante;
    }

    public String getNomeAnunciante() {
        return nomeAnunciante;
    }

    public void setNomeAnunciante(String nomeAnunciante) {
        this.nomeAnunciante = nomeAnunciante;
    }

    public PosicaoBanner getPosicao() {
        return posicao;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public void setPosicao(PosicaoBanner posicao) {
        this.posicao = posicao;
    }

    public TipoMedia getTipoMedia() {
        return tipoMedia;
    }

    public void setTipoMedia(TipoMedia tipoMedia) {
        this.tipoMedia = tipoMedia;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getImagemRelativa() {
        return id + imagem + "." + tipoMedia.getExtensao();
    }
}
