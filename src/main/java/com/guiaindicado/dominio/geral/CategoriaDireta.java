package com.guiaindicado.dominio.geral;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;
import com.guiaindicado.dominio.item.Categoria;

@Entity
@Table(name = "categoria_direta")
public class CategoriaDireta {
	
	@Id
	@Column(name = "categoria_direta_id", updatable = false)
	private Integer id;
	private String nome;
	
	@ManyToMany(targetEntity = Categoria.class)
    @JoinTable(
        name = "categoria_direta_x_categoria",
        joinColumns = @JoinColumn(name = "categoria_direta_id"),
        inverseJoinColumns = @JoinColumn(name="categoria_id")
    )
    private Collection<Categoria> categorias = Sets.newHashSet();
	
	CategoriaDireta() {}
	
	public String getNome() {
        return nome;
    }
	
	public Collection<Categoria> getCategorias() {
	    return ImmutableList.copyOf(categorias);
	}
	
	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof CategoriaDireta)) {
			return false;
		}

		CategoriaDireta aquele = (CategoriaDireta) outro;
		return (id.compareTo(aquele.id) == 0);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("nome", nome)
			.toString();
	}
}
