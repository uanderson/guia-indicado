package com.guiaindicado.ui.modelo;

import com.google.common.base.Objects;

public class Categoria {

    private Integer id;
    private String nome;
    private String referencia;
    private Integer total;
    private Integer categoriaDireta;

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

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
    
    public Integer getCategoriaDireta() {
        return categoriaDireta;
    }

    public void setCategoriaDireta(Integer categoriaDireta) {
        this.categoriaDireta = categoriaDireta;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object outro) {
        if (this == outro) {
            return true;
        }

        if (!(outro instanceof Categoria)) {
            return false;
        }

        Categoria aquele = (Categoria) outro;
        return Objects.equal(id, aquele.id);
    }
}
