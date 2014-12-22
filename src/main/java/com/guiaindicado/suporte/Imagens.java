package com.guiaindicado.suporte;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

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
    public static BufferedImage redimensionar(BufferedImage imagem, int largura, int altura) 
            throws IOException {
        
        BufferedImage resultado = null;
        int larguraOriginal = imagem.getWidth();
        int alturaOriginal = imagem.getHeight();

        boolean redimensionarImagem = false;
        boolean cortarImagem = false;

        if (altura == alturaOriginal && largura == larguraOriginal) {
            redimensionarImagem = false;
        } else {
            if (altura < alturaOriginal && largura < larguraOriginal) {
                redimensionarImagem = true;
            }
            if (altura < alturaOriginal || largura < larguraOriginal) {
                cortarImagem = true;
            }
        }

        if (redimensionarImagem || cortarImagem) {
            double escalaX;
            double escalaY;
            float proporcao = (float) alturaOriginal / (float) larguraOriginal;
           
            if ((altura / proporcao) < largura) {
                escalaY = (double) largura / larguraOriginal;
                escalaX = escalaY;
            } else {
                escalaY = (double) altura / alturaOriginal;
                escalaX = escalaY;
            }
            
            larguraOriginal = (int) Math.rint(escalaX * larguraOriginal);
            alturaOriginal = (int) Math.rint(escalaY * alturaOriginal);

            resultado = redimensionar(larguraOriginal, alturaOriginal, escalaX, escalaY, imagem);
        }

        if (alturaOriginal > altura) {
            int compensacaoY = (int) Math.rint((alturaOriginal - altura) / 2);
            resultado = resultado.getSubimage(0, compensacaoY, larguraOriginal, altura);
        } else if (larguraOriginal > largura) {
            int compensacaoX = (int) Math.rint((larguraOriginal - largura) / 2);
            resultado = resultado.getSubimage(compensacaoX, 0, largura, alturaOriginal);
        }

        return resultado;
    }

    /**
     * Redimensiona a imagem baseado nos parâmetros passados.
     * 
     * @param largura Largura da nova imagem
     * @param altura Altura da nova imagem
     * @param escalaX Total para dimensionar a imagem no eixo X
     * @param escalaY Total para dimensionar a imagem no eixo Y
     * @param imagem Imagem original
     * @return Imagem redimensionada
     */
    private static BufferedImage redimensionar(
            int largura, int altura, double escalaX, double escalaY, BufferedImage imagem) {
        
        BufferedImage resultado = new BufferedImage(largura, altura, imagem.getType());
        Graphics2D g2d = null;
        
        try {
            g2d = resultado.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.scale(escalaX, escalaY);
            g2d.drawImage(imagem, 0, 0, null);
        } finally {
            if (g2d != null) {
                g2d.dispose();
            }
        }
        
        return resultado;
    }
    
    /**
     * Escreve uma imagem JPEG para o disco.
     * 
     * @param imagem Imagem bufferizada
     * @param arquivoDestino Arquivo destino da imagem
     * @throws IOException
     */
    public static void armazenarJpeg(BufferedImage imagem, File arquivoDestino) throws IOException {
        Iterator<ImageWriter> escritores = ImageIO.getImageWritersByFormatName("JPG");
        
        if (escritores.hasNext()) {
            ImageWriter escritor = escritores.next();
            ImageWriteParam param = escritor.getDefaultWriteParam();
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(0.8F);

            FileImageOutputStream saida = new FileImageOutputStream(arquivoDestino);
            escritor.setOutput(saida);
            IIOImage image = new IIOImage(imagem, null, null);
            escritor.write(null, image, param);

            saida.close();
        }
    }

    private Imagens() {
    }
}
