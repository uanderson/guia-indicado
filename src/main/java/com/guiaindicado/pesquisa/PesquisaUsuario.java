package com.guiaindicado.pesquisa;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.guiaindicado.ui.modelo.Usuario;

/**
 * Pesquisa por usuários de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaUsuario extends PesquisaHibernate {

    /**
     * SQL para busca do usuário por email.
     */
    private static final String SQL_POR_EMAIL = 
        "SELECT nome AS nome, cidade.id AS cidade, cidade.nome AS nomeCidade," +
        " cidade.estado.sigla AS siglaEstado, email AS email, receberEmail AS receberEmail " +
        "FROM Usuario WHERE email = :email";
    
    /**
     * Pesquisa o usuário por e-mail. 
     * 
     * @param email E-mail do usuário
     * @return Usuário caso encontrado, null caso contrário
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> pesquisarPorEmail(String email) {
        return Optional.fromNullable((Usuario) getSessao()
            .createQuery(SQL_POR_EMAIL)
            .setString("email", email)
            .setResultTransformer(Transformers.aliasToBean(Usuario.class))
            .uniqueResult());
    }
}
