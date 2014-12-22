package com.guiaindicado.dominio.usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "permissao")
public class Permissao {
	
    public static final Permissao USUARIO = new Permissao(1, "ROLE_USUARIO");
    public static final Permissao ADMINISTRADOR = new Permissao(2, "ROLE_ADMINISTRADOR");
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permissao_id", updatable = false)
	private Integer id;
	private String nome;

	Permissao() {}
	
	private Permissao(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public int hashCode() {
		return nome.hashCode();
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof Permissao)) {
			return false;
		}

		Permissao aquele = (Permissao) outro;
		return nome.equals(aquele.nome);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("nome", nome)
			.toString();
	}
}
