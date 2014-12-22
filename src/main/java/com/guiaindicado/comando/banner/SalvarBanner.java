package com.guiaindicado.comando.banner;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.URL;

import com.google.common.base.Objects;

public class SalvarBanner {

    private Integer id;

    @NotEmpty(message = "{salvarBanner.url.obrigatorio}")
    @Size(max = 255, message = "{salvarBanner.url.tamanhoMaximo}")
    @URL(message = "{salvarBanner.url.invalido}")
    private String url;
    
    @Min(value = 1, message = "{salvarBanner.anunciante.obrigatorio}")
    private Integer anunciante;

    @NotEmpty(message = "{salvarBanner.posicao.obrigatorio}")
    private String posicao;

    @NotNull(message = "{salvarBanner.banner.obrigatorio}")
    private String banner;

    public SalvarBanner() {
        id = 0;
        anunciante = 0;
        posicao = "";
        banner = "";
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = Objects.firstNonNull(url, "");
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Objects.firstNonNull(id, 0);
    }

    public Integer getAnunciante() {
        return anunciante;
    }

    public void setAnunciante(Integer anunciante) {
        this.anunciante = Objects.firstNonNull(anunciante, 0);
    }

    public String getPosicao() {
        return posicao;
    }

    public void setPosicao(String posicao) {
        this.posicao = Objects.firstNonNull(posicao, "");
    }
    
    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = Objects.firstNonNull(banner, "");
    }
}
