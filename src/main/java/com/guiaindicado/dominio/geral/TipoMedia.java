package com.guiaindicado.dominio.geral;


/**
 * Define os formatos que um banner pode ter.
 * 
 * @author Uanderson Soares Gonçalves
 */
public enum TipoMedia {
    
    /**
     * Quando um formato não consegue ser identificado. Por padrão, não é suportado.
     */
    INDEFINIDO("", "") {
        /**
         * Não suportado.
         */
        @Override public String getExtensao() {
            throw new UnsupportedOperationException("Não suportado para o formato indefinido");
        }
    },
    JPEG("image/jpeg", "jpg"),
    PNG("image/png", "png"),
    GIF("image/gif", "gif"),
    SWF("application/x-shockwave-flash", "swf");
    
    private String tipo;
    private String extensao;
    
    /**
     * Define os valores padrões para o tipo de media.
     * 
     * @param tipo Tipo da media (mime type)
     * @param extensao Extensão para o tipo de media
     */
    TipoMedia(String tipo, String extensao) {
        this.tipo = tipo;
        this.extensao = extensao;
    }
    
    /**
     * Determina o formato baseado no tipo do passado (mime type), por exemplo: JPEG:
     * image/jpeg.
     * 
     * @param tipo Tipo do banner
     * @return novo TipoMedia
     */
    public static TipoMedia determinar(String tipo) {
        for (TipoMedia formato : values()) {
            if (formato.tipo.equals(tipo)) {
                return formato;
            }
        }
            
        return INDEFINIDO;
    }

    public String getNome() {
        return name();
    }
    
    public String getTipo() {
        return tipo;
    }

    public String getExtensao() {
        return extensao;
    }
}