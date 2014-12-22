package com.guiaindicado.pesquisa;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PesquisaHibernate {

    @Autowired private SessionFactory sessionFactory;

    protected Session getSessao() {
        return sessionFactory.getCurrentSession();
    }

    protected int contar(String nomeEntidade) {
        return ((Long) getSessao().createQuery("SELECT COUNT(*) FROM " + nomeEntidade)
            .uniqueResult()).intValue();
    }
}
