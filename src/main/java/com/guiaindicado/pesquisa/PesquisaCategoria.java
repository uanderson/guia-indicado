package com.guiaindicado.pesquisa;

import java.util.Collection;

import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.ui.modelo.Categoria;
import com.guiaindicado.ui.modelo.CategoriaDireta;

/**
 * Pesquisa as categorias de várias formas diferentes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class PesquisaCategoria extends PesquisaHibernate {

    /**
     * SQL para pesquisa da categoria por nome.
     */
    private static final String SQL_POR_NOME = 
        "SELECT id AS id, nome AS nome FROM Categoria WHERE LOWER(nome) LIKE :nome";
    
    /**
     * SQL para a seleção de categorias diretas.
     */
    private static final String SQL_CATEGORIAS_DIRETA = 
        "SELECT id AS id, nome AS nome FROM CategoriaDireta";
    
    /**
     * SQL para a seleção de categorias, baseado nas categorias diretas.
     */
    private static final String SQL_CATEGORIAS = 
        "SELECT cat.id AS id, cat.nome AS nome, cat.referencia AS referencia, cd.id AS categoriaDireta " +
        " FROM CategoriaDireta AS cd JOIN cd.categorias AS cat WHERE cd.id in (:diretas)";
    
    /**
     * Pesquisa das cateogrias por nome. No máximo 5 resultado serão apresentados.
     * 
     * @param nome Nome da categoria
     * @return Coleção de categorias
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<Categoria> pesquisarPorNome(String nome) {
        return getSessao().createQuery(SQL_POR_NOME)
            .setParameter("nome", "%" + nome + "%")
            .setMaxResults(5)
            .setResultTransformer(Transformers.aliasToBean(Categoria.class))
            .list();
    }
    
    /**
     * Pesquisa as categorias diretas, no máximo 3.
     * 
     * @return Coleção de categorias diretas
     */
    @SuppressWarnings("unchecked")
    @Transactional(readOnly = true)
    public Collection<CategoriaDireta> pesquisarCategoriasDiretas() {
        Collection<CategoriaDireta> categoriasDiretas = getSessao()
            .createQuery(SQL_CATEGORIAS_DIRETA)
            .setMaxResults(3)
            .setResultTransformer(Transformers.aliasToBean(CategoriaDireta.class))
            .list();
        
        if (categoriasDiretas.isEmpty()) {
            return ImmutableList.of();
        }
        
        Collection<Integer> ids = Lists.newArrayList();
        
        for (final CategoriaDireta categoriaDireta : categoriasDiretas) {
            ids.add(categoriaDireta.getId());
        }
        
        Collection<Categoria> categorias = getSessao().createQuery(SQL_CATEGORIAS)
            .setParameterList("diretas", ids)
            .setResultTransformer(Transformers.aliasToBean(Categoria.class))
            .list();
        
        for (final CategoriaDireta categoriaDireta : categoriasDiretas) {
           Iterable<Categoria> filtradas = Iterables.filter(categorias, new Predicate<Categoria>() {
               @Override public boolean apply(Categoria categoria) {
                   return categoriaDireta.getId().equals(categoria.getCategoriaDireta());
               }
            });
            
           categoriaDireta.setCategorias(ImmutableList.copyOf(filtradas));
        }
        
        return categoriasDiretas;
    }
}
