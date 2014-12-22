package com.guiaindicado.dominio.publicidade;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioBanner extends RepositorioHibernate {

    public void salvar(Banner banner) {
        getSessao().saveOrUpdate(banner);
    }
    
    public Banner getPorId(int id) {
        return (Banner) getSessao().get(Banner.class, id);
    }
}
