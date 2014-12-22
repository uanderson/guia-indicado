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
@Table(name = "cep")
public class Cep {
	
	@Id
	@Column(name = "cep_id", updatable = false)
	private String id;
	private String rua;
	private String bairro;
	
	@ManyToOne
	@JoinColumn(name = "cidade_id")
	private Cidade cidade;
	
	Cep() {}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof Cep)) {
			return false;
		}

		Cep aquele = (Cep) outro;
		return (id.compareTo(aquele.id) == 0);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("rua", rua)
			.append("bairro", bairro)
			.toString();
	}
}
