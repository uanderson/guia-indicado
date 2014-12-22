package com.guiaindicado.dominio.item;

import org.springframework.stereotype.Repository;

import com.guiaindicado.dominio.geral.RepositorioHibernate;

@Repository
public class RepositorioImagem extends RepositorioHibernate {

    public void salvar(ImagemEmpresa imagemEmpresa) {
        getSessao().saveOrUpdate(imagemEmpresa);
    }

    public ImagemEmpresa getPorId(int id) {
        return (ImagemEmpresa) getSessao().get(ImagemEmpresa.class, id);
    }

    public void remover(ImagemEmpresa imagem) {
        getSessao().delete(imagem);
    }

    public boolean temImagemPrincipal(Empresa empresa) {
        Long total = (Long) getSessao()
            .createQuery(
                "SELECT COUNT(*) FROM ImagemEmpresa WHERE empresa.id = :empresa AND principal = true")
            .setParameter("empresa", empresa.getId()).uniqueResult();

        return (total == 1);
    }

    public int contarImagens(Empresa empresa) {
        return ((Long) getSessao()
            .createQuery("SELECT COUNT(*) FROM ImagemEmpresa WHERE empresa.id = :empresa")
            .setParameter("empresa", empresa.getId())
            .uniqueResult()).intValue();
    }

    public ImagemEmpresa elegerImagemPrincipal(ImagemEmpresa original, Empresa empresa) {
        return (ImagemEmpresa) getSessao()
            .createQuery("FROM ImagemEmpresa WHERE id != :id AND empresa.id = :empresa")
            .setMaxResults(1)
            .setParameter("empresa", empresa.getId())
            .setParameter("id", original.getId())
            .uniqueResult();
    }

    public void removerPrincipalidade(Empresa empresa) {
        getSessao()
            .createQuery("UPDATE ImagemEmpresa SET principal = false WHERE empresa.id = :empresa")
            .setParameter("empresa", empresa.getId())
            .executeUpdate();
    }
    
    public ImagemEmpresa getImagemPrincipal(Empresa empresa) {
        return (ImagemEmpresa) getSessao()
            .createQuery("FROM ImagemEmpresa WHERE principal = true AND empresa.id = :empresa")
            .setParameter("empresa", empresa.getId())
            .uniqueResult();    
    }
}
