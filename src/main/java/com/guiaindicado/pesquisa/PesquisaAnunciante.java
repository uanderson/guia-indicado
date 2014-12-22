package com.guiaindicado.pesquisa;

import java.util.Collection;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.ResultadoPaginado;
import com.guiaindicado.ui.modelo.Anunciante;

/**
 * Pesquisa pelas s de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaAnunciante extends PesquisaHibernate {

    /**
     * SQL para a busca páginada de anunciantes.
     */
    private static final String SQL_PAGINADO = 
        "SELECT id AS id, nome AS nome FROM Anunciante";
    
    /**
     * SQL para a busca dos cinco anunciantes recentes.
     */
    private static final String SQL_DESTAQUE = 
        "SELECT id AS id, nome AS nome, imagem AS imagem FROM Anunciante " +
        " ORDER BY dataHoraCadastro DESC";
    
    /**
     * SQL para busca por nome.
     */
    private static final String SQL_POR_NOME = 
        "SELECT id AS id, nome AS nome FROM Anunciante WHERE LOWER(nome) LIKE :nome";
    
    /**
     * SQL para busca por id.
     */
    private static final String SQL_POR_ID =
        "SELECT id AS id, nome AS nome, cidade.id AS cidade, cidade.nome AS nomeCidade," +
        " cidade.estado.sigla AS siglaEstado, telefone AS telefone, email AS email, " +
        " site AS site FROM Anunciante WHERE id = :id";
    
    /**
     * Pesquisa todos as anunciantes paginando-as.
     * 
     * @param pagina Dados da página para consulta
     * @return Resultado paginado
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ResultadoPaginado<Anunciante> pesquisar(Pagina pagina) {
        int total = contar("Anunciante");
        pagina.definirInicio(total);
        
        Collection<Anunciante> colecao = getSessao().createQuery(SQL_PAGINADO)
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .setResultTransformer(Transformers.aliasToBean(Anunciante.class))
            .list();
        
        return ResultadoPaginado.criar(pagina, colecao, total);
    }

    /**
     * Pesquisa o anunciante pelo nome.
     * 
     * @param nome Nome do anunciante
     * @return Coleção de empresas
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<Anunciante> pesquisarPorNome(String nome) {
        return getSessao().createQuery(SQL_POR_NOME)
            .setMaxResults(8)
            .setParameter("nome", "%" + nome + "%")
            .setResultTransformer(Transformers.aliasToBean(Anunciante.class))
            .list();
    }
    
    /**
     * Pesquisa os anunciantes destaque na home.
     * 
     * @return Resultado paginado
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<Anunciante> pesquisarDestaque() {
        return getSessao().createQuery(SQL_DESTAQUE)
            .setMaxResults(5)
            .setResultTransformer(Transformers.aliasToBean(Anunciante.class))
            .list();
    }
    
    /**
     * Realiza a pesquisa do anunciante pelo id.
     * 
     * @param id ID do anunciante
     * @return Objeto opcional com anunciante caso encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Anunciante> pesquisarPorId(Integer id) {
        return Optional.fromNullable((Anunciante) getSessao()
            .createQuery(SQL_POR_ID)
            .setParameter("id", id)
            .setResultTransformer(Transformers.aliasToBean(Anunciante.class))
            .uniqueResult());
    }
}
