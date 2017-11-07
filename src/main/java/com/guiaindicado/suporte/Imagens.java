package com.guiaindicado.suporte;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * Métodos utilitários para tratamento de imagens.
 * 
 * @author Uanderson Soares Gonçalves
 */
public final class Imagens {

    /**
     * Redimensiona e corta uma imagem baseado nos parâmetros passados.
     * 
     * @param imagem Imagem original
     * @param largura Largura da nova imagem
     * @param altura Altura da nova imagem
     * @return Imagem redimensionada e cortada
     * @throws IOException
     */
    public static BufferedImage redimensionar(BufferedImage imagem, int largura, int altura) throws IOException {
        return Thumbnails.of(imagem)
            .size(largura, altura)
            .crop(Positions.CENTER)
            .asBufferedImage();
    }
    
    /**
     * Escreve uma imagem JPEG para o disco.
     * 
     * @param imagem Imagem bufferizada
     * @param arquivoDestino Arquivo destino da imagem
     * @throws IOException
     */
    public static void armazenarJpeg(BufferedImage imagem, File arquivoDestino) throws IOException {
        ImageIO.write(imagem, "jpg", arquivoDestino);
    }
}
