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
@Table(name = "estado")
public class Estado {
	
	@Id
	@Column(name = "estado_id", updatable = false)
	private Integer id;
	private String sigla;
	private String nome;
	
	@ManyToOne
	@JoinColumn(name = "pais_id")
	private Pais pais;

	Estado() {}
	
    public String getSigla() {
        return sigla;
    }
    
    public String getNome() {
        return nome;
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

		if (!(outro instanceof Estado)) {
			return false;
		}

		Estado aquele = (Estado) outro;
		return (id.compareTo(aquele.id) == 0);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("sigla", sigla)
			.append("nome", nome)
			.append("pais", pais)
			.toString();
	}
}
