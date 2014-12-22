package com.guiaindicado.ui.modelo;

import com.google.common.base.Objects;

public class Cidade {

    private Integer id;
    private String nome;
    private String siglaEstado;

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

    public String getSiglaEstado() {
        return siglaEstado;
    }

    public void setSiglaEstado(String siglaEstado) {
        this.siglaEstado = siglaEstado;
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

        if (!(outro instanceof Cidade)) {
            return false;
        }

        Cidade aquele = (Cidade) outro;
        return Objects.equal(id, aquele.id);
    }
}