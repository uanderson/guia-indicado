package com.guiaindicado.ui.modelo;

import com.guiaindicado.dominio.geral.TipoMedia;

public class Imagem {

    private Integer id;
    private String nome;
    private Boolean principal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPrincipal() {
        return principal;
    }

    public void setPrincipal(Boolean principal) {
        this.principal = principal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagemRelativaPequena() {
        return nome + "-p." + TipoMedia.JPEG.getExtensao();
    }
    
    public String getImagemRelativaBusca() {
        return nome + "-b." + TipoMedia.JPEG.getExtensao();
    }
    
    public String getImagemRelativaMedia() {
        return nome + "-m." + TipoMedia.JPEG.getExtensao();
    }
}
