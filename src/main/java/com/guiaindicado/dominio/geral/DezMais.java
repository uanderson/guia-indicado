package com.guiaindicado.dominio.geral;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.guiaindicado.dominio.item.Empresa;

@Entity
@Table(name = "dez_mais")
public class DezMais {
	
	@Id
	@Column(name = "dez_mais_id", updatable = false)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	DezMais() {}

	public Integer getId() {
	    return id;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof DezMais)) {
			return false;
		}

		DezMais aquele = (DezMais) outro;
		return Objects.equal(id, aquele.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.toString();
	}
}
