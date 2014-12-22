package com.guiaindicado.dominio.publicidade;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioAnunciante extends RepositorioHibernate {

    public void salvar(Anunciante anunciante) {
        getSessao().saveOrUpdate(anunciante);
    }
    
    public Anunciante getPorId(int id) {
        return (Anunciante) getSessao().get(Anunciante.class, id);
    }
}
