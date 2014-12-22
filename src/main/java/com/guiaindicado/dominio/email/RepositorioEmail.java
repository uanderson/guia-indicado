package com.guiaindicado.dominio.email;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.email.Email.Status;
import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioEmail extends RepositorioHibernate {

    public void salvar(Email email) {
        getSessao().saveOrUpdate(email);
    }

    @SuppressWarnings("unchecked")
    public Collection<Email> listarPorStatus(Email.Status status) {
        return getSessao().createQuery("FROM Email WHERE status = :status")
            .setParameter("status", status).list();
    }
    
    public void removerPorStatus(Status status) {
        getSessao().createQuery("DELETE FROM Email WHERE status = :status")
            .setParameter("status", status).executeUpdate();
    }
}
