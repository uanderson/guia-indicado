package com.guiaindicado.pesquisa;

import java.util.Collection;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.ResultadoPaginado;
import com.guiaindicado.ui.modelo.Banner;

/**
 * Realiza a pesquisa de banners de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaBanner extends PesquisaHibernate {

    /**
     * SQL para pesquisa do banner por id.
     */
    private static final String SQL_POR_ID = 
        "SELECT id AS id, posicao AS posicao, tipoMedia AS tipoMedia, ativo AS ativo," +
        " anunciante.id AS anunciante, anunciante.nome AS nomeAnunciante, url AS url" +
        " FROM Banner WHERE id = :id";
    
    /**
     * SQL para montagem da banneria.
     */
    private static final String SQL_BANNERIA = 
        "SELECT id AS id, url AS url, imagem AS imagem, posicao AS posicao, " +
        " tipoMedia AS tipoMedia, ativo AS ativo, anunciante.id AS anunciante, " +
        " anunciante.nome AS nomeAnunciante FROM Banner WHERE posicao = :posicao " +
        " AND ativo = true ORDER BY RAND()";
        
    /**
     * SQL para pesquisa paginada.
     */
    private static final String SQL_PAGINADO = 
        "SELECT id AS id, posicao AS posicao, tipoMedia AS tipoMedia, ativo AS ativo," +
        " anunciante.id AS anunciante, anunciante.nome AS nomeAnunciante, url AS url" +
        " FROM Banner";
    
    /**
     * Pesquisa um banner por id.
     * 
     * @param id Identificador do banner
     * @return Objeto opcional com banner caso encontrado
     */
    @Transactional(readOnly = true)
    public Optional<Banner> pesquisarPorId(int id) {
        return Optional.fromNullable((Banner) getSessao()
            .createQuery(SQL_POR_ID)
            .setInteger("id", id)
            .setResultTransformer(Transformers.aliasToBean(Banner.class))
            .uniqueResult());
    }
    
    /**
     * Pesquisa o banner por posição.
     * 
     * @return Posição do banner
     */
    @Transactional(readOnly = true)
    public Banner pesquisarPorPosicao(PosicaoBanner posicao) {
        return (Banner) getSessao().createQuery(SQL_BANNERIA)
            .setParameter("posicao", posicao)
            .setMaxResults(1)
            .setResultTransformer(Transformers.aliasToBean(Banner.class))
            .uniqueResult();
    }
    
    /**
     * Pesquisa todos os banners paginando-os.
     * 
     * @param pagina Dados da página para consulta
     * @return Resultado paginado
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ResultadoPaginado<Banner> pesquisar(Pagina pagina) {
        int total = contar("Banner");
        pagina.definirInicio(total);
        
        Collection<Banner> colecao = getSessao().createQuery(SQL_PAGINADO)
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .setResultTransformer(Transformers.aliasToBean(Banner.class))
            .list();
        
        return ResultadoPaginado.criar(pagina, colecao, total);
    }
}
