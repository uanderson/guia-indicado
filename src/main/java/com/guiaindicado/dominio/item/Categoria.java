package com.guiaindicado.dominio.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "categoria")
public class Categoria {
	
	@Id
	@Column(name = "categoria_id", updatable = false)
	private Integer id;
	private String nome;
	private String referencia;
	
	Categoria() {}
	
	public Integer getId() {
	    return id;
	}
	
	public String getNome() {
        return nome;
    }
	
	public String getReferencia() {
	    return referencia;
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

		if (!(outro instanceof Categoria)) {
			return false;
		}

		Categoria aquele = (Categoria) outro;
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
