package com.guiaindicado.dominio.localizacao;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "cidade")
public class Cidade {
	
	@Id
	@Column(name = "cidade_id", updatable = false)
	private Integer id;
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "estado_id")
	private Estado estado;
	
	Cidade() {}

	public Integer getId() {
	    return id;
	}
	
	public String getNome() {
	    return nome;
	}
	
    public Estado getEstado() {
        return estado;
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

		if (!(outro instanceof Cidade)) {
			return false;
		}

		Cidade aquele = (Cidade) outro;
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
