package com.guiaindicado.pesquisa;

import java.util.Collection;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.ui.modelo.Imagem;

/**
 * Pesquisa as imagens de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaImagem extends PesquisaHibernate {

    /**
     * SQL para a pesquisa por empresa.
     */
    private static final String SQL_POR_EMPRESA =
        "SELECT id AS id, nome AS nome, principal AS principal FROM " +
        " ImagemEmpresa WHERE empresa.id = :empresa";
    
    /**
     * Pesquisa as imagens que uma empresa possuí.
     * 
     * @param empresa ID da empresa
     * @return Coleção de imagens
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<Imagem> pesquisarPorEmpresa(Integer empresa) {
        return getSessao().createQuery(SQL_POR_EMPRESA)
            .setParameter("empresa", empresa)
            .setResultTransformer(Transformers.aliasToBean(Imagem.class))
            .list();
    }
}
