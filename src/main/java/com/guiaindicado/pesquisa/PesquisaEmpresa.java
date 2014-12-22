package com.guiaindicado.pesquisa;

import java.util.Collection;

import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.guiaindicado.dominio.avaliacao.RepositorioAvaliacao;
import com.guiaindicado.dominio.avaliacao.SinteseAvaliacao;
import com.guiaindicado.dominio.item.Empresa.Status;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.ResultadoPaginado;
import com.guiaindicado.ui.modelo.Empresa;

/**
 * Pesquisa empresas de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaEmpresa extends PesquisaHibernate {

    @Autowired private RepositorioAvaliacao repositorioAvaliacao;

    /**
     * SQL para pesquisa de todos os itens paginados.
     */
    private static final String SQL_PESQUISAR =
        "SELECT id AS id, nome AS nome, categoria.nome AS nomeCategoria, CAST(status AS int) AS status " +
        " FROM Empresa ORDER BY categoria, nome";
    
    /**
     * SQL para a pesquisa de empresas pelo nome.
     */
    private static final String SQL_POR_NOME = 
        "SELECT id AS id, nome AS nome FROM Empresa WHERE LOWER(nome) LIKE :nome" +
        " AND status = :status";
    
    /**
     * SQL para a pesquisa das dez mais da semana.
     */
    private static final String SQL_DEZ_MAIS =
        "SELECT empresa.id AS id, empresa.nome AS nome, empresa.referencia AS referencia " +
        " FROM DezMais";
    
    /**
     * SQL para pesquisa por id e id/referencia.
     */
    private static final String SQL_POR_ID = 
        "SELECT id AS id, nome AS nome, descricao AS descricao, categoria.id AS categoria," +
        " referencia AS referencia, categoria.nome AS nomeCategoria," +
        " categoria.referencia AS referenciaCategoria, tags AS tags, cep AS cep," +
        " endereco AS endereco, numero AS numero, bairro AS bairro, cidade.id AS cidade," +
        " cidade.nome AS nomeCidade, cidade.estado.nome AS nomeEstado," +
        " cidade.estado.sigla AS siglaEstado, telefone AS telefone, celular AS celular," +
        " email AS email, site AS site, latitude AS latitude, longitude AS longitude" +
        " FROM Empresa WHERE id = :id ";
    
    /**
     * Pesquisa todos as empresas paginando-as.
     * 
     * @param pagina Dados da página para consulta
     * @return Resultado paginado
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ResultadoPaginado<Empresa> pesquisar(Pagina pagina) {
        int total = contar("Empresa");
        pagina.definirInicio(total);
        
        Collection<Empresa> colecao = getSessao().createQuery(SQL_PESQUISAR)
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .setResultTransformer(Transformers.aliasToBean(Empresa.class))
            .list();
        
        return ResultadoPaginado.criar(pagina, colecao, total);
    }
    
    /**
     * Pesquisa a empresa pelo nome. O número máximo retornado é de 5.
     * 
     * @param nome Nome da empresa
     * @return Coleção de empresas
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<Empresa> pesquisarPorNome(String nome) {
        return getSessao().createQuery(SQL_POR_NOME)
            .setParameter("status", Status.MODERADO)
            .setParameter("nome", "%" + nome + "%")
            .setMaxResults(5)
            .setResultTransformer(Transformers.aliasToBean(Empresa.class)).list();
    }
    
    /**
     * Pesquisa as dez melhores empresas da semana.
     * 
     * @return As 10 melhores empresas.
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<Empresa> pesquisarDezMais() {
        return getSessao().createQuery(SQL_DEZ_MAIS)
            .setMaxResults(10)
            .setResultTransformer(Transformers.aliasToBean(Empresa.class))
            .list();
    }
    
    /**
     * Realiza a pesquisa da empresa pelo ID.
     * 
     * @param id ID da empresa
     * @return Objeto opcional com dado da empresa
     */
    @Transactional(readOnly = true)
    public Optional<Empresa> pesquisarPorId(Integer id) {
        return Optional.fromNullable((Empresa) getSessao()
            .createQuery(SQL_POR_ID)
            .setParameter("id", id)
            .setResultTransformer(Transformers.aliasToBean(Empresa.class))
            .uniqueResult());
    }

    /**
     * Realiza a pesquisa da empresa pelo ID e pela referência.
     * 
     * @param ID
     * @param referencia
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Empresa> pesquisarDetalhe(int id, String referencia) {
        Empresa empresa = (Empresa) getSessao()
            .createQuery(SQL_POR_ID + " AND referencia = :referencia")
            .setParameter("id", id)
            .setParameter("referencia", referencia)
            .setResultTransformer(Transformers.aliasToBean(Empresa.class))
            .uniqueResult();
        
        if (empresa != null) {
            SinteseAvaliacao sintese = repositorioAvaliacao.sintetisar(id);
            
            empresa.setMediaAvaliacao(sintese.getMedia());
            empresa.setAvaliacoes(sintese.getTotal());
        }
        
        return Optional.fromNullable(empresa);
    }
}
