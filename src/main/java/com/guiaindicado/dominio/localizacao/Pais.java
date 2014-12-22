package com.guiaindicado.dominio.localizacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "pais")
public class Pais {
	
	@Id
	@Column(name = "pais_id", updatable = false)
	private Integer id;
	private String sigla;
	private String nome;
	
	Pais() {}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof Pais)) {
			return false;
		}

		Pais aquele = (Pais) outro;
		return (id.compareTo(aquele.id) == 0);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("sigla", sigla)
			.append("nome", nome)
			.toString();
	}
}
