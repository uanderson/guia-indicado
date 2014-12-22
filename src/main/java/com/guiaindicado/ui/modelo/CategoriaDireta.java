package com.guiaindicado.ui.modelo;

import java.util.Collection;

import com.google.common.collect.Lists;

public class CategoriaDireta {

    private Integer id;
    private String nome;
    private Collection<Categoria> categorias = Lists.newArrayList();

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

    public Collection<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Collection<Categoria> categorias) {
        this.categorias = categorias;
    }
}
