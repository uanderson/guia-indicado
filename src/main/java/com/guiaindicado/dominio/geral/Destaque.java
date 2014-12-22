package com.guiaindicado.dominio.geral;

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
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.servico.fs.FSArquivo;

@Entity
@Table(name = "destaque")
public class Destaque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "destaque_id", updatable = false)
    private Integer id;
	private String titulo;
	private String texto;
	private Boolean vertical;
	private String imagem;
	
	@Column(name = "data_hora_inicio")
	private Date dataHoraInicio;
	
	@Column(name = "data_hora_termino")
	private Date dataHoraTermino;

	@ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;
	
	Destaque() {
	    this.vertical = false;
        this.dataHoraInicio = new Date();
    }
	
    public static Destaque criar() {
        return new Destaque();
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
     * Verifica se o destaque tem imagem associada a si.
     * 
     * @return true caso tenha imagem, false caso contrário
     */
    public boolean temImagem() {
        return (id != null);
    }
    
    /**
     * Verifica se o destaque pode ser inativado. Para ter sucesso na inativação, 1 destaque
     * vertical e 4 horizontais devem estar ativos. 
     * 
     * @param verticaisAtivos Total de destaques verticais ativos
     * @param horizontaisAtivos Total de destaques horizontais ativos
     * @return true se for inativável, false caso contrário
     */
    public boolean inativavel(int verticaisAtivos, int horizontaisAtivos) {
        if (!vertical && horizontaisAtivos > 4) {
            return true;
        }
        
        if (vertical && verticaisAtivos > 1) {
            return true;
        }
        
        return false;
    }
    
    /**
     * Inativa um destaque setando a data/hora de término para a atual.
     */
    public void inativar() {
        if (dataHoraTermino == null) {
            dataHoraTermino = new Date();
        }
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
        if (imagem == null || imagem.isEmpty()) {
            throw new IllegalArgumentException("Imagem é obrigatória para gerar o arquivo.");
        }
        
        return FSArquivo.construtor()
            .comNome(imagem)
            .comTipoMedia(TipoMedia.JPEG)
            .comBase("destaque")
            .construir();
    }

    public Destaque setTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public Destaque setTexto(String texto) {
        this.texto = texto;
        return this;
    }

    public Destaque setVertical(Boolean vertical) {
        this.vertical = vertical;
        return this;
    }
    
    public String getImagem() {
        return imagem;
    }
    
    public Destaque setImagem(String imagem) {
        this.imagem = Preconditions.checkNotNull(imagem);
        return this;
    }

    public Destaque setEmpresa(Empresa empresa) {
        this.empresa = empresa;
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

        if (!(outro instanceof Destaque)) {
            return false;
        }

        Destaque aquele = (Destaque) outro;
        return Objects.equal(id, aquele.id);
    }
	
   @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("titulo", titulo)
            .append("texto", texto)
            .append("vertical", vertical)
            .append("imagem", imagem)
            .append("dataHoraInicio", dataHoraInicio)
            .append("dataHoraTermino", dataHoraTermino)
            .toString();
    }
   
   /**
    * Determina as dimensões da imagem do destaque.
    */
    public static enum Imagem {

        VERTICAL(187, 346),
        HORIZONTAL(223, 171);

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
