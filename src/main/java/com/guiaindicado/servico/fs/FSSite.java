package com.guiaindicado.servico.fs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.io.Files;
import com.guiaindicado.suporte.Imagens;

/**
 * Processa e mantém o repositório de arquivos do site, como banners e destaques.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class FSSite {

    @Value("#{environment['site.fs.imagem']}") String dirImg;
    @Value("#{environment['site.fs.imagem.temp']}") String dirTemp;

    /**
     * Armazena uma imagem no sistema de arquivos.
     * 
     * @param imagem Imagem a ser armazenada
     * @param arquivo Arquivo informativo
     * @throws IOException
     */
    public void armazenar(BufferedImage imagem, FSArquivo arquivo) throws IOException {
        File dirDestino = new File(dirImg, arquivo.getBase());

        if (!dirDestino.exists()) {
            dirDestino.mkdirs();
        }
        
        File destino = new File(dirDestino, arquivo.getNome());
        Imagens.armazenarJpeg(imagem, destino);        
    }
    
    /**
     * Cria um arquivo temporário do site.
     * 
     * @param entrada InputStream do arquivo a ser armazenado
     * @return ID do arquivo temporário
     * @throws IOException
     */
    public String armazenarTemp(byte[] bytes) throws IOException {
        File diretorio = new File(dirTemp);
        
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        
        String id = gerarId();
        Files.write(bytes, new File(diretorio, id));
        
        return id;
    }
    
    /**
     * Gera um ID único para armazenar a imagem.
     * 
     * @return ID para o arquivo
     */
    public String gerarId() {
        byte[] bytesDataHora = String.valueOf(System.currentTimeMillis()).getBytes();
        return UUID.nameUUIDFromBytes(bytesDataHora).toString().replace("-", "");
    }
    
    /**
     * Remove um arquivo do sistema de armazenamento.
     * 
     * @param arquivo Arquivo a ser removido
     */
    public void remover(FSArquivo arquivo) {
        File diretorio = new File(dirImg, arquivo.getBase());
        File arquivoTemp = new File(diretorio, arquivo.getNome());
        
        if (arquivoTemp.exists()) {
            arquivoTemp.delete();
        }
    }
    
    /**
     * Remove um arquivo temporário caso ele exista.
     * 
     * @param id ID do arquivo temporário
     */
    public void removerTemp(String id) {
        File arquivoTemp = new File(dirTemp, id);
        
        if (arquivoTemp.exists()) {
            arquivoTemp.delete();
        }
    }
    
    /**
     * Move um arquivo gerado temporariamente para o destino final.
     * 
     * @param id ID do arquivo temporário
     * @param arquivo Dados sobre o novo arquivo
     */
    public void moverTemp(String id, FSArquivo arquivo) throws IOException {
        File dirDestino = new File(dirImg, arquivo.getBase());

        if (!dirDestino.exists()) {
            dirDestino.mkdirs();
        }
        
        File origem = new File(dirTemp, id);
        File destino = new File(dirDestino, arquivo.getNome());
        Files.move(origem, destino);
    }
    
    /**
     * Obtém o arquivo temporário armazenado anteriormente.
     * 
     * @param id ID do arquivo temporário 
     * @return Referência ao arquivo temporário
     */
    public File getTemp(String id) {
        if (id == null || id.isEmpty()) {
            return new File(dirTemp, "-undefined-");
        }
        
        return new File(dirTemp, id);
    }
}
