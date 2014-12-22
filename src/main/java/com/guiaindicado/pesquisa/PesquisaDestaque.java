package com.guiaindicado.pesquisa;

import java.util.Collection;
import java.util.List;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.suporte.ResultadoPaginado;
import com.guiaindicado.ui.modelo.Destaque;
import com.guiaindicado.ui.modelo.GrupoDestaque;

/**
 * Pesquisa destaques de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaDestaque extends PesquisaHibernate {

    /**
     * SQL para pesquisa páginada dos destaques.
     */
    private static final String SQL_PAGINADO = 
        "SELECT id AS id, titulo AS titulo, texto AS texto, vertical AS vertical, " +
        " dataHoraInicio AS dataHoraInicio, dataHoraTermino AS dataHoraTermino," +
        " empresa.id AS empresa, empresa.nome AS nomeEmpresa, imagem AS imagem" +
        " FROM Destaque ORDER BY dataHoraInicio";
    
    /**
     * SQL para pesquisa do destaque por ID.
     */
    private static final String SQL_POR_ID = 
        "SELECT id AS id, titulo AS titulo, texto AS texto, vertical AS vertical, " +
        " empresa.id AS empresa, empresa.nome AS nomeEmpresa, imagem AS imagem" +
        " FROM Destaque WHERE id = :id";
    
    /**
     * SQL para pesquisa de grupos de destaque.
     */
    private static final String SQL_GRUPOS = 
        "SELECT id AS id, titulo AS titulo, texto AS texto, vertical AS vertical," +
        "  imagem AS imagem, empresa.id AS empresa, empresa.referencia AS referenciaEmpresa " +
        " FROM Destaque WHERE dataHoraTermino IS NULL AND vertical = :vertical ORDER BY dataHoraInicio";
    
    /**
     * Pesquisa um destaque por ID.
     * 
     * @param id ID do destaque
     * @return Destaque se for encontrado, null caso contrário
     */
    @Transactional(readOnly = true)
    public Optional<Destaque> pesquisarPorId(int id) {
        return Optional.fromNullable((Destaque) getSessao()
            .createQuery(SQL_POR_ID)
            .setInteger("id", id)
            .setResultTransformer(Transformers.aliasToBean(Destaque.class))
            .uniqueResult());
    }
    
    /**
     * Pesquisa todos os destaques paginando-os.
     * 
     * @param pagina Dados da página para consulta
     * @return Resultado paginado
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public ResultadoPaginado<Destaque> pesquisar(Pagina pagina) {
        int total = contar("Destaque");
        pagina.definirInicio(total);
        
        Collection<Destaque> colecao = getSessao().createQuery(SQL_PAGINADO)
            .setFirstResult(pagina.getInicio())
            .setMaxResults(pagina.getTamanho())
            .setResultTransformer(Transformers.aliasToBean(Destaque.class))
            .list();
        
        return ResultadoPaginado.criar(pagina, colecao, total);
    }
    
    /**
     * Pesquisa e monta os grupos de destaque ativos. Cada grupo é composto por 1 destaque
     * vertical e 4 horizontais.
     * 
     * @return Coleção com os grupos
     */
    @Transactional(readOnly = true)
    @SuppressWarnings("unchecked")
    public Collection<GrupoDestaque> pesquisarGrupos() {
        Collection<Destaque> verticais = getSessao().createQuery(SQL_GRUPOS)
            .setMaxResults(3)
            .setBoolean("vertical", true)
            .setResultTransformer(Transformers.aliasToBean(Destaque.class))
            .list();
        
        Collection<Destaque> horizontais = getSessao().createQuery(SQL_GRUPOS)
            .setMaxResults(12)
            .setBoolean("vertical", false)
            .setResultTransformer(Transformers.aliasToBean(Destaque.class))
            .list();

        int tamanhoVertical = 1;
        int tamanhoHorizontal = 4;
        
        if (verticais.size() < tamanhoVertical || horizontais.size() < tamanhoHorizontal) {
            return ImmutableList.of();
        }

        int gruposVerticais = verticais.size();
        int gruposHorizontais = (int) Math.ceil((double) horizontais.size() / tamanhoHorizontal);

        if (gruposVerticais < gruposHorizontais) {
            repetir(verticais, (gruposHorizontais - gruposVerticais));
        }

        int horizontaisFaltantes = (verticais.size() * tamanhoHorizontal) - horizontais.size();
        
        if (horizontaisFaltantes > 0) {
            repetir(horizontais, horizontaisFaltantes);
        }
        
        return gerarGruposDestaque(verticais, horizontais);
    }

    /**
     * Repete os itens já existente na coleção até alcaçar o número de elementos contidos na adição.
     * Por exemplo: se a coleção tem 2 itens e deve ser adicionado mais dois, o método irá duplicar
     * os itens já existentes.
     * 
     * @param colecao Coleção a ser repetida
     * @param adicao Número de itens que devem ser repetidos
     */
    private void repetir(Collection<Destaque> colecao, int adicao) {
        int repeticaoMaxima = colecao.size();
        int repeticao = 0;
        
        for (int indice = 0; indice < adicao; indice++) {
            if (repeticao == repeticaoMaxima) {
                repeticao = 0;
            }
            
            colecao.add(Iterables.get(colecao, repeticao++));
        }
    }

    /**
     * Processa as listas de destaques verticais e horizontais combinando-as em um grupo de destaque
     * com 1 destaque vertical e 4 horizontais.
     * 
     * @param verticais Destaques verticais
     * @param horizontais Destaques horizontais
     * @return Coleção com grupo de destaques
     */
    private Collection<GrupoDestaque> gerarGruposDestaque(Collection<Destaque> verticais, 
            Collection<Destaque> horizontais) {

        Iterable<List<Destaque>> particao = Iterables.partition(horizontais, 4);
        Collection<GrupoDestaque> grupos = Lists.newArrayList();
        
        int totalVerticais = verticais.size();
        
        for (int indice = 0; indice < totalVerticais; indice++) {
            Collection<Destaque> destaques = Lists.newArrayList();
            destaques.add(Iterables.get(verticais, indice));
            destaques.addAll(Iterables.get(particao, indice));
            
            GrupoDestaque grupo = new GrupoDestaque();
            grupo.setDestaques(destaques);
            grupos.add(grupo);
        }
        
        return grupos;
    }
}
