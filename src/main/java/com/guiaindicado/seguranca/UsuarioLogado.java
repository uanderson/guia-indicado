package com.guiaindicado.seguranca;

import java.util.Collection;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

@SuppressWarnings({ "serial" })
public final class UsuarioLogado implements UsuarioSite {

    private Integer id;
	private String nome;
	private String username;
	private String senha;
	private Boolean habilitado;
	private Collection<PermissaoSite> permissoes = Sets.newHashSet();

	private UsuarioLogado(Construtor construtor) {
	    id = construtor.id;
		nome = construtor.nome;
		username = construtor.username;
		senha = construtor.senha;
		habilitado = construtor.habilitado;
	}
	
	public Integer getId() {
	    return id;
	}
	
	@Override
	public String getNome() {
        return nome;
    }
	
	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public boolean isEnabled() {
		return habilitado;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@Override
    public boolean isLogado() {
        return true;
    }

    @Override
	public Collection<PermissaoSite> getAuthorities() {
		return ImmutableList.copyOf(permissoes);
	}
	
	public void addPermissoes(Collection<PermissaoSite> permissao) {
		Preconditions.checkNotNull(permissao);
		this.permissoes.addAll(permissao);
	}

	@Override
	public int hashCode() {
		return username.hashCode();
	}

	@Override
	public boolean equals(Object outro) {
		if (this == outro) {
			return true;
		}

		if (!(outro instanceof UsuarioLogado)) {
			return false;
		}

		UsuarioLogado aquele = (UsuarioLogado) outro;
		return username.equals(aquele.username);
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("nome", nome)
			.append("username", username)
			.append("senha", senha)
			.append("habilitado", habilitado)
			.append("permissoes", permissoes)
			.toString();
	}

	public static Construtor construtor() {
		return new Construtor();
	}

	public static final class Construtor {

		private Integer id;
		private String nome;
		private String username;
		private String senha;
		private Boolean habilitado;

		public Construtor comId(Integer id) {
			this.id = id;
			return this;
		}
		
		public Construtor comNome(String nome) {
            this.nome = nome;
            return this;
        }
		
		public Construtor comUsername(String username) {
			this.username = username;
			return this;
		}
		
		public Construtor comSenha(String senha) {
			this.senha = senha;
			return this;
		}

		public Construtor comHabilitado(Boolean habilitado) {
			this.habilitado = habilitado;
			return this;
		}

		public UsuarioLogado construir() {
			Preconditions.checkNotNull(id);
			Preconditions.checkNotNull(username);
			Preconditions.checkNotNull(nome);
			Preconditions.checkNotNull(senha);
			Preconditions.checkNotNull(habilitado);

			return new UsuarioLogado(this);
		}
	}
}