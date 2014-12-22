package com.guiaindicado.servico.fs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Preconditions;
import com.guiaindicado.dominio.geral.TipoMedia;

/**
 * Define um arquivo do site como: banner e destaque.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class FSArquivo {

    private String nome;
    private TipoMedia tipoMedia;
    private String base;
    private String prefixo;
    private String sufixo;

    /**
     * Novo FSArquivo baseado no construtor. 
     * 
     * @param construtor Construtor com dados para o arquivo
     */
    private FSArquivo(Construtor construtor) {
        nome = construtor.nome;
        tipoMedia = construtor.tipoMedia;
        base = construtor.base;
        prefixo = construtor.prefixo;
        sufixo = construtor.sufixo;
    }
    
    /**
     * Transforma os dados do arquivo em um nome único, levando em consideração o prefixo
     * e sufixo.
     * 
     * @return Nome gerado
     */
    public String getNome() {
        StringBuilder sb = new StringBuilder();
        
        if (!prefixo.isEmpty()) {
            sb.append(prefixo);
            sb.append("-");
        }
        
        sb.append(nome);
        
        if (!sufixo.isEmpty()) {
            sb.append("-");
            sb.append(sufixo);
        }
        
        sb.append(".");
        sb.append(tipoMedia.getExtensao());
        
        return sb.toString();
    }
    
    /**
     * Obtém o local padrão onde o arquivo irá ficar partindo do local definido pelo
     * {@link FSSite}.
     * 
     * @return
     */
    public String getBase() {
        return base;
    }

    /**
     * Altera o prefixo para o nome da imagem. O prefixo é adicionado no formato "prefixo-".
     * 
     * @param prefixo Prefixo para o nome da imagem
     */
    public void alterarPrefixo(String prefixo) {
        this.prefixo = Preconditions.checkNotNull(prefixo);
    }
    
    /**
     * Altera o sufixo para o nome da imagem. O sufixo é adicionado no formato "-sufixo".
     * @param sufixo
     */
    public void alterarSufixo(String sufixo) {
        this.sufixo = Preconditions.checkNotNull(sufixo);
    }
    
    /**
     * Cria um construtor de FSArquivo.
     * 
     * @return Novo construtor
     */
    public static Construtor construtor() {
        return new Construtor();
    }
    
    /**
     * Mantém dados para construção do FSArquivo. 
     */
    public static final class Construtor {
        
        private String nome;
        private TipoMedia tipoMedia;
        private String base = "";
        private String prefixo = "";
        private String sufixo = "";
        
        public Construtor comNome(String nome) {
            this.nome = Preconditions.checkNotNull(nome);
            return this;
        }
        
        public Construtor comTipoMedia(TipoMedia tipoMedia) {
            this.tipoMedia = Preconditions.checkNotNull(tipoMedia);
            return this;
        }
        
        public Construtor comBase(String base) {
            this.base = Preconditions.checkNotNull(base);
            return this;
        }
        
        public Construtor comPrefixo(String prefixo) {
            this.prefixo = Preconditions.checkNotNull(prefixo).trim();
            return this;            
        }
        
        public Construtor comSufixo(String sufixo) {
            this.sufixo = Preconditions.checkNotNull(sufixo).trim();
            return this;            
        }
        
        public FSArquivo construir() {
            Preconditions.checkNotNull(nome);
            Preconditions.checkNotNull(tipoMedia);
            return new FSArquivo(this);
        }
    }
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("nome", nome)
            .append("tipoMedia", tipoMedia)
            .append("base", base)
            .append("prefixo", prefixo)
            .append("sufixo", sufixo)
            .toString();
    }
}
