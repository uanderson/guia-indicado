package com.guiaindicado.comando.destaque;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.google.common.base.Objects;

public class SalvarDestaque {

    private Integer id;
    
    @Min(value = 1, message = "{salvarDestaque.empresa.obrigatorio}")
    private Integer empresa;
    
    @NotBlank(message = "{salvarDestaque.titulo.obrigatorio}")
    @Size(max = 18, message = "{salvarDestaque.titulo.tamanhoMaximo}")
    private String titulo;
    
    @NotBlank(message = "{salvarDestaque.texto.obrigatorio}")
    @Size(max = 26, message = "{salvarDestaque.texto.tamanhoMaximo}")
    private String texto;

    private Boolean vertical;
    private String imagem;
    
    public SalvarDestaque() {
        id = 0;
        empresa = 0;
        titulo = "";
        texto = "";
        imagem = "";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = Objects.firstNonNull(id, 0);
    }

    public Integer getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Integer empresa) {
        this.empresa = Objects.firstNonNull(empresa, 0);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = Objects.firstNonNull(titulo, "");
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = Objects.firstNonNull(texto, "");
    }

    public Boolean getVertical() {
        return vertical;
    }

    public void setVertical(Boolean vertical) {
        this.vertical = Objects.firstNonNull(vertical, false);
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = Objects.firstNonNull(imagem, "");
    }
}
