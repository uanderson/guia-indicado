package com.guiaindicado.pesquisa;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.guiaindicado.ui.modelo.Cep;

/**
 * Pesquisa por CEPs de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaCep extends PesquisaHibernate {

    /**
     * SQL para pesquisa por número do CEP.
     */
    private static final String SQL_POR_CEP = 
        "SELECT id AS id, rua AS endereco, bairro AS bairro, cidade.id AS cidade, " +
        "  cidade.nome AS nomeCidade, cidade.estado.sigla AS siglaEstado " +
        "FROM Cep WHERE id = :cep";
    
    /**
     * Pesquisa o CEP pelo número.
     * 
     * @param cep Número do CEP
     * @return CEP se encontrado, null caso contrário
     */
    @Transactional(readOnly = true)
    public Optional<Cep> pesquisarPorCep(String cep) {
        return Optional.fromNullable((Cep) getSessao()
            .createQuery(SQL_POR_CEP)
            .setParameter("cep", cep)
            .setResultTransformer(Transformers.aliasToBean(Cep.class))
            .uniqueResult());
    }
}
