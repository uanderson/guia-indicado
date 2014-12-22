package com.guiaindicado.dominio.usuario;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioUsuario extends RepositorioHibernate {

    public void salvar(Usuario usuario) {
        getSessao().saveOrUpdate(usuario);
    }
    
    public Usuario getPorEmail(String email) {
        return (Usuario) getSessao().createQuery("FROM Usuario WHERE email = :email")
            .setParameter("email", email).uniqueResult();
    }

    public boolean usuarioCadastrado(String email) {
        Long total = (Long) getSessao()
            .createQuery("SELECT COUNT(*) FROM Usuario WHERE email = :email")
            .setParameter("email", email).uniqueResult();

        return (total == 1);
    }
}
