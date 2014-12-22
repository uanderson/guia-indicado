package com.guiaindicado.seguranca;

import java.util.Collection;

@SuppressWarnings({ "serial" })
public final class UsuarioAnonimo implements UsuarioSite {

    private UsuarioAnonimo() {}
    
    public static UsuarioAnonimo criar() {
        return new UsuarioAnonimo();
    }
    
	@Override
	public String getNome() {
	    throw notificarNaoSuportado();
    }
	
	@Override
	public String getUsername() {
	    throw notificarNaoSuportado();
	}

	@Override
	public String getPassword() {
	    throw notificarNaoSuportado();
	}

	@Override
	public boolean isEnabled() {
	    throw notificarNaoSuportado();
	}

	@Override
	public boolean isAccountNonExpired() {
	    throw notificarNaoSuportado();
	}

	@Override
	public boolean isAccountNonLocked() {
	    throw notificarNaoSuportado();
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    throw notificarNaoSuportado();
	}

	@Override
    public boolean isLogado() {
        return false;
    }
	
	@Override
	public Collection<PermissaoSite> getAuthorities() {
	    throw notificarNaoSuportado();
	}
	
	private RuntimeException notificarNaoSuportado() {
	    throw new UnsupportedOperationException("Usuário anônimo. Esse dado não está disponível");
	}
}