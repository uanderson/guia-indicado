package com.guiaindicado.dominio.publicidade;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.common.base.Preconditions;
import com.guiaindicado.dominio.geral.TipoMedia;

/**
 * Determina a posição do banner no site.
 */
public enum PosicaoBanner {

    /**
     * Quando uma posição não é identificada.
     */
    INDEFINIDA(0, 0, "") {
        
        /**
         * Não tem tamanho suportado, por ser indefinida.
         */
        @Override public boolean dimensaoSuportada(TipoMedia formato, File arquivo) {
            return false;
        }
        
    },
    TOPO(468, 60, "Topo"),
    HOME(640, 70, "Home"),
    LATERAL(300, 250, "Lateral");

    private int largura;
    private int altura;
    private String descricao;

    PosicaoBanner(int largura, int altura, String descricao) {
        this.largura = largura;
        this.altura = altura;
        this.descricao = descricao;
    }

    /**
     * Verifica se o arquivo possuí as dimensões necessárias para o site.
     * 
     * @param media Tipo de media
     * @param arquivo Arquivo para verificação
     * @return true se tiver as dimensões certas, false caso contrário
     */
    public boolean dimensaoSuportada(TipoMedia media, File arquivo) {
        if (TipoMedia.INDEFINIDO.equals(media)) {
            return false;
        }
        
        if (TipoMedia.SWF.equals(media)) {
            return true;
        }
        
        try {
            BufferedImage imagem = ImageIO.read(arquivo);
            
            if (imagem.getWidth() == largura && imagem.getHeight() == altura) {
                return true;
            } else {
                return false;
            }
        } catch (IOException ex) {
            return false;
        }
    }
    
    /**
     * Determina a posição baseado na String.
     * 
     * @param posicao Posição do banner
     * @return Posição do banner
     */
    public static PosicaoBanner determinar(String posicao) {
        try {
            return valueOf(Preconditions.checkNotNull(posicao));
        } catch (IllegalArgumentException ex) {
            return INDEFINIDA;
        }
    }
    
    public int getLargura() {
        return largura;
    }

    public int getAltura() {
        return altura;
    }
    
    public String getDescricao() {
        return descricao;
    }
}