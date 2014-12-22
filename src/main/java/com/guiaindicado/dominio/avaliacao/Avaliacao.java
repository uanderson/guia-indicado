package com.guiaindicado.dominio.avaliacao;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.usuario.Usuario;

@Entity
@Table(name = "avaliacao")
public class Avaliacao {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "avaliacao_id", updatable = false)
	private Integer id;
	private Integer nota;
	
	@Column(name = "data_hora")
    private Date dataHora;
	private String ip;
	
	@ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
	
	@ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

	Avaliacao() {
	    dataHora = new Date();
	}
	
	public static Avaliacao criar() {
	    return new Avaliacao();
	}
	
	public boolean notaAceitavel(int nota) {
        return (nota >= 1) && (nota <= 5);
    }
	
    public Avaliacao setNota(Integer nota) {
        this.nota = nota;
        return this;
    }

    public Avaliacao setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Avaliacao setUsuario(Usuario usuario) {
        this.usuario = usuario;
        return this;
    }

    public Avaliacao setEmpresa(Empresa empresa) {
        this.empresa = empresa;
        return this;
    }

    @Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}

		if (!(other instanceof Avaliacao)) {
			return false;
		}

		Avaliacao that = (Avaliacao) other;
		return Objects.equal(id, that.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("nota", nota)
			.append("dataHora", dataHora)
			.append("ip", ip)
			.toString();
	}
}
