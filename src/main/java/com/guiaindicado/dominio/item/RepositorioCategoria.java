package com.guiaindicado.dominio.item;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioCategoria extends RepositorioHibernate {

    public Categoria getPorId(Integer id) {
        return (Categoria) getSessao().get(Categoria.class, id);
    }
}
