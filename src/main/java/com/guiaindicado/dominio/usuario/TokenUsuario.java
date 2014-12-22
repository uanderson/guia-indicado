package com.guiaindicado.dominio.usuario;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.time.DateUtils;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;

@Entity
@Table(name = "token_usuario")
public class TokenUsuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "token_usuario_id", updatable = false)
	private Integer id;
	private String token;
	
	@Column(name = "data_hora")
	private Date dataHora;
	
	@Enumerated
	private Tipo tipo;
	
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;

	TokenUsuario() {
	}
	
	private TokenUsuario(Usuario usuario, Tipo tipo) {
	    this.token = gerarToken();
	    this.tipo = Preconditions.checkNotNull(tipo);
	    this.usuario = Preconditions.checkNotNull(usuario);
	    this.dataHora = new Date();
	}

	public static TokenUsuario criar(Usuario usuario, Tipo tipo) {
	    return new TokenUsuario(usuario, tipo);
	}
	
	private String gerarToken() {
		Date data = new Date();
		UUID uuid = UUID.nameUUIDFromBytes(String.valueOf(data).getBytes());
		return uuid.toString().replaceAll("-", "");
	}
    
	public boolean valido() {
	    return tipo.valido(dataHora);
	}
	
	public String getToken() {
	    return token;
	}
	
	public Usuario getUsuario() {
	    return usuario;
	}
	
	public Date getDataHora() {
	    return new Date(dataHora.getTime());
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

		if (!(outro instanceof TokenUsuario)) {
			return false;
		}

		TokenUsuario aquele = (TokenUsuario) outro;
		return Objects.equal(id, aquele.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("token", token)
			.append("dataHora", dataHora)
			.append("tipo", tipo)
			.toString();
	}
	
	public static enum Tipo {
	    
	    CONFIRMACAO_CADASTRO {
	        @Override
	        public boolean valido(Date dataHora) {
	            return dataHora.before(DateUtils.addDays(dataHora, 30));
	        }  
	    },
	    ESQUECI_SENHA {
	        @Override
	        public boolean valido(Date dataHora) {
	            return dataHora.before(DateUtils.addDays(dataHora, 1));
	        }
	    };
	    
	    public abstract boolean valido(Date dataHora);
	}
}
