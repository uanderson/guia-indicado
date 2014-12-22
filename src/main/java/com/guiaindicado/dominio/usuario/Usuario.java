package com.guiaindicado.dominio.usuario;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.seguranca.Seguranca;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "usuario_id", updatable = false)
	private Integer id;
	private String nome;
	private String email;
	private String senha;
	private Boolean habilitado;
	
	@Column(name = "receber_email")
	private Boolean receberEmail;

	@Column(name = "data_cadastro")
	private Date dataCadastro;
	
	@ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
	
	@ManyToMany
	@JoinTable(
		name = "usuario_x_permissao",
	    joinColumns = @JoinColumn(name = "usuario_id"),
	    inverseJoinColumns = @JoinColumn(name="permissao_id")
	)
    @Cascade({ CascadeType.SAVE_UPDATE, CascadeType.MERGE, CascadeType.DELETE })
    private Collection<Permissao> permissoes = Sets.newHashSet();

	Usuario() {
	}
	
	private Usuario(String email) {
	    this.email = email;
	    this.senha = "";
        this.habilitado = false;
        this.receberEmail = true;
        this.dataCadastro = new Date();
        this.permissoes.add(Permissao.USUARIO);
	}
	
	public static Usuario criar(String email) {
	    return new Usuario(Preconditions.checkNotNull(email));
	}
	
	public void definirSenha(String senha) {
	    this.senha = Seguranca.codificarSenha(senha, email);
	}
	
	public boolean compararSenha(String senha) {
	    return this.senha.equals(Seguranca.codificarSenha(senha, email));
	}
	
	public void habilitar() {
	    this.habilitado = true;
	}
	
    public String getNome() {
        return nome;
    }
    
    public Usuario setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getEmail() {
        return email;
    }
    
    public Cidade getCidade() {
        return cidade;
    }
    
    public Usuario setCidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }
    
    public Boolean getReceberEmail() {
        return receberEmail;
    }
    
    public Usuario setReceberEmail(Boolean receberEmail) {
        this.receberEmail = receberEmail;
        return this;
    }
    
    public Boolean isHabilitado() {
        return habilitado;
    }
	
	@Override
	public int hashCode() {
		return email.hashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Usuario)) {
			return false;
		}

		Usuario that = (Usuario) other;
		return email.equals(that.email);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("nome", nome)
			.append("email", email)
			.append("senha", senha)
			.append("habilitado", habilitado)
			.append("receberEmail", receberEmail)
			.toString();
	}
}
