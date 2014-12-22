package com.guiaindicado.dominio.item;

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
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.servico.fs.FSArquivo;

@Entity
@Table(name = "imagem")
public class ImagemEmpresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imagem_id", updatable = false)
    private Integer id;
    private String nome;
	private Boolean principal;
	
	@ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

	ImagemEmpresa() {
    }
    
	private ImagemEmpresa(Empresa empresa) {
	    this.principal = false;
	    this.empresa = empresa;
	}
	
    public static ImagemEmpresa criar(Empresa empresa) {
        return new ImagemEmpresa(empresa);
    }

    public void tornarPrincipal() {
        principal = true;
    }
    
	public Integer getId() {
        return id;
    }
	
    public ImagemEmpresa setNome(String nome) {
        this.nome = Preconditions.checkNotNull(nome);
        return this;
    }

    public Boolean getPrincipal() {
        return principal;
    }
    
    public Empresa getEmpresa() {
        return empresa;
    }
    
    /**
     * Cria um novo arquivo informativo para persistência física da empresa. Para sucesso
     * na criação do arquivo, é necessário que o banner esteja persistido e um ID
     * disponível, caso contrário uma exceção será lançada.
     * 
     * @return Novo arquivo informatio
     * @throws IllegalArgumentException
     */
    public FSArquivo criarArquivo() {
        if (nome == null || nome.isEmpty()) {
            throw new IllegalStateException("Referencia é obrigatória para gerar o arquivo.");
        }
        
        if (empresa == null) {
            throw new IllegalStateException("Empresa é obrigatória para gerar arquivo");
        }
        
        return FSArquivo.construtor()
            .comNome(nome)
            .comTipoMedia(TipoMedia.JPEG)
            .comBase("empresa/" + empresa.getId())
            .construir();
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

        if (!(outro instanceof ImagemEmpresa)) {
            return false;
        }

        ImagemEmpresa aquele = (ImagemEmpresa) outro;
        return Objects.equal(id, aquele.id);
    }
	
   @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("id", id)
            .append("nome", nome)
            .append("principal", principal)
            .toString();
    }
   
    public static enum Dimensao {

        PEQUENA(107, 86),
        BUSCA(117, 95),
        MEDIA(317, 256);

        private int largura;
        private int altura;

        Dimensao(int largura, int altura) {
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
