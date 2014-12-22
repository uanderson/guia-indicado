package com.guiaindicado.dominio.localizacao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;
import com.guiaindicado.suporte.Pagina;

@Repository
public class RepositorioCidade extends RepositorioHibernate {

    public Cidade getPorId(Integer id) {
        return (Cidade) getSessao().get(Cidade.class, id);
    }
    
    @SuppressWarnings("unchecked")
    public Collection<Cidade> listar(Pagina pagina) {
        return getSessao().createQuery("FROM Cidade")
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .list();
    }

    public int contar() {
        return ((Long) getSessao().createQuery(
            "SELECT COUNT(*) FROM Cidade").uniqueResult()).intValue();
    }
}
