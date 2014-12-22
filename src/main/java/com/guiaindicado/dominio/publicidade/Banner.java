package com.guiaindicado.dominio.publicidade;

import java.util.Collection;

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
import com.guiaindicado.servico.fs.FSArquivo;

/**
 * Representa um banner no site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Entity
@Table(name = "banner")
public class Banner {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banner_id", updatable = false)
	private Integer id;
	private String imagem;
	private String url;
	
	@Enumerated
	private PosicaoBanner posicao;
	
	@Enumerated
	@Column(name = "tipo_media")
	private TipoMedia tipoMedia;
	private Boolean ativo;
	
	@ManyToOne
    @JoinColumn(name = "anunciante_id")
    private Anunciante anunciante;
	
	Banner() {
	    ativo = true;
	}
	
	/**
	 * Cria um novo banner.
	 * 
	 * @return Novo banner
	 */
	public static Banner criar() {
	    return new Banner();
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
	    return Lists.newArrayList(TipoMedia.JPEG,  TipoMedia.PNG, TipoMedia.GIF, TipoMedia.SWF);
	}
	
    /**
     * Verifica se o tamanho do arquivo de banner é permitido para armazenagem. O máximo
     * permitido é de 50kb.
     * 
     * @param tamanho Tamanho da imagem que será armazenada
     * @return true se for compatível, false caso contrário
     */
    public static boolean tamanhoPermitido(long tamanho) {
        return tamanho <= 50;
    }
	
    /**
     * Obtém o tamanho máximo permitido para um banner.
     * 
     * @return Tamanho permitido
     */
    public static int tamanhoPermitido() {
        return 50;
    }
    
    /**
     * @see #criarArquivo(String)
     */
    public FSArquivo criarArquivo() {
        return criarArquivo(imagem);
    }
    
    /**
     * Cria um novo arquivo informativo para persistência física do destaque. Para sucesso
     * na criação do arquivo, é necessário que o destaque esteja persistido e um ID
     * disponível, caso contrário uma exceção será lançada.
     * 
     * @return Novo arquivo informatio
     * @throws IllegalArgumentException
     */
    public FSArquivo criarArquivo(String imagem) {
        if (id == null) {
            throw new IllegalArgumentException("ID é obrigatório para gerar o arquivo");
        }
        
        if (imagem == null || imagem.isEmpty()) {
            throw new IllegalArgumentException("Imagem é obrigatória para gerar o arquivo.");
        }
        
        if (tipoMedia == null) {
            throw new IllegalArgumentException("Tipo de media é obrigatório para gerar o arquivo");
        }
        
        return FSArquivo.construtor()
            .comNome(id + imagem)
            .comTipoMedia(tipoMedia)
            .comBase("banner")
            .construir();
    }
    
    /**
     * Realiza a inativação do banner.
     */
	public void inativar() {
	    ativo = false;
	}
	
	public Banner setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getImagem() {
        return imagem;
    }
	
	public Banner setImagem(String imagem) {
        this.imagem = imagem;
        return this;
    }

    public Banner setPosicao(PosicaoBanner posicao) {
        this.posicao = posicao;
        return this;
    }
    
    public Banner setTipoMedia(TipoMedia tipoMedia) {
        this.tipoMedia = tipoMedia;
        return this;
    }

    public Banner setAnunciante(Anunciante anunciante) {
        this.anunciante = anunciante;
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

		if (!(other instanceof Banner)) {
			return false;
		}

		Banner that = (Banner) other;
		return Objects.equal(id, that.id);
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", id)
			.append("imagem", imagem)
			.append("url", url)
			.append("posicao", posicao)
			.append("tipoMedia", tipoMedia)
			.append("ativo", ativo)
			.toString();
	}
}
