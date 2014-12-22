package com.guiaindicado.dominio.email;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Objects;

@Entity
@Table(name = "email")
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id", updatable = false)
    private Long id;
	private String para;
	private String assunto;
	private String conteudo;
	
	@Column(name = "data_hora")
	private Date dataHora;
	
	@Enumerated
	private Status status;
	
    Email() {
    }
    
    private Email(Date dataHora) {
        this.dataHora = dataHora;
        this.status = Status.PENDENTE;
    }
    
    public static Email criar() {
        return new Email(new Date());
    }
    
    public void enviar() {
        status = Status.ENVIADO;
    }
    
	public String getPara() {
		return para;
	}
	
	public Email setPara(String para) {
	    this.para = para;
	    return this;
	}
	
	public String getAssunto() {
		return assunto;
	}
	
	public Email setAssunto(String assunto) {
	    this.assunto = assunto;
	    return this;
	}
	
	public String getConteudo() {
		return conteudo;
	}
	
	public Email setConteudo(String conteudo) {
	    this.conteudo = conteudo;
	    return this;
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

        if (!(outro instanceof Email)) {
            return false;
        }

        Email aquele = (Email) outro;
        return Objects.equal(id, aquele.id);
    }
	
   @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("para", para)
            .append("assunto", assunto)
            .append("conteudo", conteudo)
            .append("dataHora", dataHora)
            .toString();
    }
	
    public static enum Status {
        PENDENTE, ENVIADO
    }
}
