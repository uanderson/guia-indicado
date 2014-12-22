package com.guiaindicado.dominio.geral;

import org.springframework.stereotype.Repository;


@Repository
public class RepositorioDestaque extends RepositorioHibernate {

    public void salvar(Destaque destaque) {
        getSessao().saveOrUpdate(destaque);
    }
    
    public Destaque getPorId(int id) {
        return (Destaque) getSessao().get(Destaque.class, id);
    }
    
    public int contarQuantidadeAtiva(boolean vertical) {
        return ((Long) getSessao().createQuery(
                "SELECT COUNT(*) FROM Destaque WHERE vertical = :vertical AND dataHoraTermino IS NULL")
            .setBoolean("vertical", vertical)
            .uniqueResult()).intValue();
    }
}
