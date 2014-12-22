package com.guiaindicado.seguranca;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

import com.google.common.base.Preconditions;

@SuppressWarnings("serial")
public class PermissaoSite implements GrantedAuthority {

    private String nome;

    private PermissaoSite(String nome) {
        this.nome = nome;
    }

    public static PermissaoSite criar(String nome) {
        return new PermissaoSite(Preconditions.checkNotNull(nome));
    }

    @Override
    public String getAuthority() {
        return nome;
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

        if (!(outro instanceof PermissaoSite)) {
            return false;
        }

        PermissaoSite aquele = (PermissaoSite) outro;
        return nome.equals(aquele.nome);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("nome", nome)
            .toString();
    }
}
