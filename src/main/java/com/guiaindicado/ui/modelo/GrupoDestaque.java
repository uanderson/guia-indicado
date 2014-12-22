package com.guiaindicado.ui.modelo;

import java.util.Collection;

import com.google.common.collect.Lists;

public class GrupoDestaque {

	private Boolean ativo;
	private Collection<Destaque> destaques = Lists.newArrayList();

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Collection<Destaque> getDestaques() {
		return destaques;
	}

	public void setDestaques(Collection<Destaque> horizontal) {
		this.destaques = horizontal;
	}
}
