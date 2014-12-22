package com.guiaindicado.dominio.item;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;
import com.guiaindicado.suporte.Pagina;

@Repository
public class RepositorioEmpresa extends RepositorioHibernate {

    public void salvar(Empresa empresa) {
        getSessao().saveOrUpdate(empresa);
    }
    
    public Empresa getPorId(int id) {
        return (Empresa) getSessao().get(Empresa.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Empresa> listar(Empresa.Status status, Pagina pagina) {
        return getSessao().createQuery("FROM Empresa WHERE status = :status")
            .setParameter("status", status)
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .list();
    }

    public int contar(Empresa.Status status) {
        return ((Long) getSessao()
            .createQuery("SELECT COUNT(*) FROM Empresa WHERE status = :status")
            .setParameter("status", status).uniqueResult()).intValue();
    }
}
