package com.guiaindicado.dominio.avaliacao;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;
import com.guiaindicado.dominio.usuario.Usuario;

@Repository
public class RepositorioAvaliacao extends RepositorioHibernate {

    public void salvar(Avaliacao avaliacao) {
        getSessao().saveOrUpdate(avaliacao);
    }
    
    public Avaliacao getPorEmpresa(Usuario usuario, int empresa) {
        return (Avaliacao) getSessao().createQuery(
            "FROM Avaliacao WHERE usuario.email = :email AND empresa.id = :empresa")
            .setString("email", usuario.getEmail())
            .setInteger("empresa", empresa)
            .uniqueResult();
    }

    public SinteseAvaliacao sintetisar(int empresa) {
        return (SinteseAvaliacao) getSessao().createQuery(
                "SELECT new com.guiaindicado.dominio.avaliacao.SinteseAvaliacao(COUNT(*), " +
                " COALESCE(AVG(nota), 0)) FROM Avaliacao WHERE empresa.id = :empresa")
            .setParameter("empresa", empresa)
            .uniqueResult();
    }
}
