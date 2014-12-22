package com.guiaindicado.dominio.publicidade;

import java.util.Collection;
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
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.servico.fs.FSArquivo;

/**
 * Representa um anunciante no site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Entity
@Table(name = "anunciante")
public class Anunciante {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "anunciante_id", updatable = false)
	private Integer id;
	private String nome;
	private String imagem;
	private String telefone;
	private String email;
	private String site;
	
	@Column(name = "data_hora_cadastro")
	private Date dataHoraCadastro;
	
	@ManyToOne
    @JoinColumn(name = "cidade_id")
    private Cidade cidade;
	
	Anunciante() {
	    dataHoraCadastro = new Date();
	}
	
	/**
	 * Cria um novo anunciante.
	 * 
	 * @return Novo anunciante
	 */
	public static Anunciante criar() {
	    return new Anunciante();
	}
	
	/**
     * Verifica se a media passada é suportada para ser um banner.
     * 
     * @param media Tipo da media a ser verificada
     * @return true se for suportada, false caso contrário
     */
    public static boolean mediaSuportada(TipoMedia media) {
        return mediasSuportadas().contains(Preconditions.checkNotNull(media));
    }
    
    /**
     * Obtém todos os tipos de media suportadas para banner.
     * 
     * @return Tipos de medias suportadas
     */
    public static Collection<TipoMedia> mediasSuportadas() {
        return Lists.newArrayList(TipoMedia.JPEG);
    }
	
    /**
     * Verifica se o destaque tem imagem associada a si.
     * 
     * @return true caso tenha imagem, false caso contrário
     */
    public boolean temImagem() {
        return (imagem != null);
    }
    
    /**
     * @see #criarArquivo(String)
     */
    public FSArquivo criarArquivo() {
        return criarArquivo(imagem);
    }
    
    /**
     * Cria um novo arquivo informativo para persistência física do anunciante. Para sucesso
     * na criação do arquivo, é necessário que o anunciante esteja persistido e um ID
     * disponível, caso contrário uma exceção será lançada.
     * 
     * @return Novo arquivo informatio
     * @throws IllegalArgumentException
     */
    public FSArquivo criarArquivo(String imagem) {
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório para gerar o arquivo.");
        }
        
        if (imagem == null || imagem.isEmpty()) {
            throw new IllegalArgumentException("Imagem é obrigatória para gerar o arquivo.");
        }
        
        return FSArquivo.construtor()
            .comNome(imagem)
            .comTipoMedia(TipoMedia.JPEG)
            .comBase("anunciante/" + id)
            .construir();
    }

    public String getNome() {
        return nome;
    }

    public Anunciante setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getImagem() {
        return imagem;
    }
    
    public Anunciante setImagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public Anunciante setTelefone(String telefone) {
        this.telefone = telefone;
        return this;
    }

    public Anunciante setEmail(String email) {
        this.email = email;
        return this;
    }

    public Anunciante setSite(String site) {
        this.site = site;
        return this;
    }

    public Anunciante setCidade(Cidade cidade) {
        this.cidade = cidade;
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

		if (!(other instanceof Anunciante)) {
			return false;
		}

		Anunciante that = (Anunciante) other;
		return Objects.equal(id, that.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("nome", nome)
			.append("imagem", imagem)
			.append("telefone", telefone)
			.append("email", email)
			.append("site", site)
			.append("dataHoraCadastro", dataHoraCadastro)
			.toString();
	}
	
	/**
	 * Define o tamanho das imagens do anunciante.
	 */
	public static enum Imagem {

        PADRAO(148, 114);

        private int largura;
        private int altura;

        Imagem(int largura, int altura) {
            this.largura = largura;
            this.altura = altura;
        }

        public int getLargura() {
            return largura;
        }

        public int getAltura() {
            return altura;
        }
    }
}
