package com.guiaindicado.ui.modelo;

import java.util.Collection;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.guiaindicado.suporte.ResultadoPaginado;

public class ResultadoBusca {

    private ResultadoPaginado<ItemBusca> resultado;
    private Collection<Categoria> categorias = Lists.newArrayList();
    
    public ResultadoPaginado<ItemBusca> getResultado() {
        return resultado;
    }

    public void setResultado(ResultadoPaginado<ItemBusca> resultado) {
        this.resultado = resultado;
    }

    public Collection<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(Collection<Categoria> categorias) {
        this.categorias = categorias;
    }
    
    public int getTotal() {
        return resultado.getTotal();
    }

    public Collection<ItemBusca> getColecao() {
        return ImmutableSet.copyOf(resultado.getColecao());
    }

    public int getPaginaCorrente() {
        return resultado.getPaginaCorrente();
    }
}
