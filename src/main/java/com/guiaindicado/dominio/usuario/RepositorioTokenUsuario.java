package com.guiaindicado.dominio.usuario;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioTokenUsuario extends RepositorioHibernate {

    public void salvar(TokenUsuario token) {
        getSessao().save(token);
    }
    
    public void remover(TokenUsuario tokenUsuario) {
        getSessao().delete(tokenUsuario);
    }

    public TokenUsuario getPorToken(String token) {
        return (TokenUsuario) getSessao()
            .createQuery("FROM TokenUsuario WHERE token = LOWER(:token)")
            .setParameter("token", token).uniqueResult();
    }
}
