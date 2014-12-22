package com.guiaindicado.dominio.item;

import java.util.Collection;
import java.util.Date;

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

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.dominio.usuario.Usuario;
import com.guiaindicado.suporte.Strings;

@Entity
@Table(name = "empresa")
public class Empresa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "empresa_id", updatable = false)
	private Integer id;
	private String nome;
	private String referencia;
	private String descricao;
	
	@ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;
	
	private String tags;
	private String cep;
	private String endereco;
	private String numero;
	private String bairro;
	
	@ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
	
	private String telefone;
	private String celular;
	private String email;
	private String site;
	private Double latitude;
	private Double longitude;
	
	@Enumerated
    private Status status;
	
	@Column(name = "data_hora_cadastro")
	private Date dataHoraCadastro;
	
	@ManyToOne
    @JoinColumn(name = "usuario_cadastro")
    private Usuario usuarioCadastro;
	
	Empresa() {
	}
	
	private Empresa(Status status, Usuario usuarioCadastro) {
	    this.status = status;
	    this.dataHoraCadastro = new Date();
	    this.usuarioCadastro = usuarioCadastro;
	}

	public static Empresa criar(Usuario usuarioCadastro) {
	    return new Empresa(Status.PENDENTE, Preconditions.checkNotNull(usuarioCadastro));
	}
	
	/**
     * Verifica se a media passada é suportada para ser um destaque.
     * 
     * @param media Tipo da media a ser verificada
     * @return true se for suportada, false caso contrário
     */
    public static boolean mediaSuportada(TipoMedia media) {
        return mediasSuportadas().contains(Preconditions.checkNotNull(media));
    }
    
    /**
     * Obtém todos os tipos de media suportadas para destaque.
     * 
     * @return Tipos de medias suportadas
     */
    public static Collection<TipoMedia> mediasSuportadas() {
        return Lists.newArrayList(TipoMedia.JPEG);
    }
	
    /**
     * Prepara a empresa para moderação.
     */
    public void revisar() {
        status = Status.REVISADO;
    }
	
    /**
     * Libera a empresa no site.
     */
    public void moderar() {
        status = Status.MODERADO;
    }
 
    /**
     * Verifica se a imagem pode ser armazenada, se não ultrapassa o total de fotos da
     * empresa.
     * 
     * @param total Total de imagens
     * @return true se puder armazenar, false caso contrário
     */
    public boolean imagemArmazenavel(int total) {
        return total <= 5;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public Empresa setNome(String nome) {
        this.nome = nome;
        return this;
    }
    
    public String getReferencia() {
        return referencia;
    }
    
    public Empresa setReferencia(String referencia) {
        Preconditions.checkNotNull(referencia);
        this.referencia = Strings.hifenizar(referencia, true).toLowerCase();
        return this;
    }

    public String getDescricao() {
        return descricao;
    }
    
    public Empresa setDescricao(String descricao) {
        this.descricao = descricao;
        return this;
    }

    public Categoria getCategoria() {
        return categoria;
    }
    
    public Empresa setCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }
    
    public String getTags() {
        return tags;
    }
    
    public Empresa setTags(String tags) {
        this.tags = Preconditions.checkNotNull(tags);
        this.tags = Strings.removerAcentoEspecial(tags);
        return this;
    }
    
    public Empresa setCep(String cep) {
        this.cep = cep;
        return this;
    }
    
    public String getEndereco() {
        return endereco;
    }

    public Empresa setEndereco(String endereco) {
        this.endereco = endereco;
        return this;
    }

    public String getNumero() {
        return numero;
    }

    public Empresa setNumero(String numero) {
        this.numero = numero;
        return this;
    }
    
    public String getBairro() {
        return bairro;
    }

    public Empresa setBairro(String bairro) {
        this.bairro = bairro;
        return this;
    }
    
    public Cidade getCidade() {
        return cidade;
    }
    
    public Empresa setCidade(Cidade cidade) {
        this.cidade = cidade;
        return this;
    }
    
    public String getTelefone() {
        return telefone;
    }

    public Empresa setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Empresa setCelular(String celular) {
        this.celular = celular;
        return this;
    }

    public Empresa setEmail(String email) {
        this.email = email;
        return this;
    }

    public Empresa setSite(String site) {
        this.site = site;
        return this;
    }

    public Empresa setLatitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public Empresa setLongitude(Double longitude) {
        this.longitude = longitude;
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

		if (!(other instanceof Empresa)) {
			return false;
		}

		Empresa that = (Empresa) other;
		return Objects.equal(id, that.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
			.append("id", id)
			.append("nome", nome)
			.append("referencia", referencia)
			.append("descricao", descricao)
			.append("tags", tags)
			.append("cep", cep)
			.append("endereco", endereco)
			.append("numero", numero)
			.append("bairro", bairro)
			.append("telefone", telefone)
			.append("celular", celular)
			.append("email", email)
			.append("site", site)
			.append("latitude", latitude)
			.append("longitude", longitude)
			.toString();
	}
	
	/**
	 * Status da empresa no site.
	 */
	public static enum Status {
	    PENDENTE, REVISADO, MODERADO
	}
}
