package com.guiaindicado.servico;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Collection;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.item.ImagemEmpresa;
import com.guiaindicado.dominio.item.RepositorioEmpresa;
import com.guiaindicado.dominio.item.RepositorioImagem;
import com.guiaindicado.servico.fs.FSArquivo;
import com.guiaindicado.servico.fs.FSSite;
import com.guiaindicado.suporte.Imagens;

/**
 * Processa as operações para armazenamento de imagens.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class ServicoImagem {

    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private RepositorioImagem repositorioImagem;
    @Autowired private FSSite fsSite;
    
    /**
     * Armazena as imagens da empresa.
     * 
     * @param idEmpresa ID da empresa
     * @param imagens Imagens a serem armazenadas
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado armazenarImagensEmpresa(int idEmpresa, Collection<MultipartFile> imagens) {
        Resultado validacao = validarImagens(imagens);
        
        if (!validacao.houveSucesso()) {
            return validacao;
        }
        
        Empresa empresa = repositorioEmpresa.getPorId(idEmpresa);
        
        if (empresa == null) {
            return Resultado.erro("A empresa informada não existe.");
        }
        
        int totalImagens = repositorioImagem.contarImagens(empresa) + imagens.size();
        
        if (!empresa.imagemArmazenavel(totalImagens)) {
            return Resultado.erro(
                 "Total de imagens foi ultrapassado. Exclua alguma imagem e tente novamente.");
        }
        
        boolean temPrincipal = repositorioImagem.temImagemPrincipal(empresa);
        
        for (MultipartFile imagem : imagens) {
            ImagemEmpresa imagemLocal = ImagemEmpresa.criar(empresa)
                .setNome(fsSite.gerarId());
            
            if (!temPrincipal) {
                imagemLocal.tornarPrincipal();
                temPrincipal = true;
            }
            
            repositorioImagem.salvar(imagemLocal);
            armazenarImagem(imagemLocal, imagem);
        }

        return Resultado.sucesso(MessageFormat.format(
            "{0} imagem(s) armazenada(s) com sucesso.", imagens.size()));
    }
    
    /**
     * Excluí uma imagem. Caso a imagem excluída seja principal, elege outra para ser principal.
     * 
     * @param idImagem
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado excluirImagem(int idImagem) {
        ImagemEmpresa imagem = repositorioImagem.getPorId(idImagem);
        
        if (imagem == null) {
            return Resultado.erro("A imagem informada não existe.");
        }
        
        Empresa empresa = imagem.getEmpresa();
        repositorioImagem.remover(imagem);
        excluirImagemFisica(imagem);
        
        if (imagem.getPrincipal()) {
            ImagemEmpresa novaPrincipal = 
                repositorioImagem.elegerImagemPrincipal(imagem, empresa);
            
            if (novaPrincipal != null) {
                novaPrincipal.tornarPrincipal();
                repositorioImagem.salvar(novaPrincipal);
            }
        }
        
        return Resultado.sucesso("Imagem excluída com sucesso.");
    }
    
    /**
     * Remove as imagens do repositório de imagens.
     * 
     * @param idImagem Imagem a ser excluída
     */
    private void excluirImagemFisica(ImagemEmpresa imagem) {
        FSArquivo arquivo = imagem.criarArquivo();
        arquivo.alterarSufixo("p");
        fsSite.remover(arquivo);
        
        arquivo = imagem.criarArquivo();
        arquivo.alterarSufixo("p");
        fsSite.remover(arquivo);
        
        arquivo = imagem.criarArquivo();
        arquivo.alterarSufixo("p");
        fsSite.remover(arquivo);
    }
    
    /**
     * Define uma imagem como principal.
     * 
     * @param idImagem
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado tornarPrincipal(int idImagem) {
        ImagemEmpresa imagemCandidata = repositorioImagem.getPorId(idImagem);
        
        if (imagemCandidata == null) {
            return Resultado.erro("A imagem informada não existe.");
        }
        
        repositorioImagem.removerPrincipalidade(imagemCandidata.getEmpresa());
        
        imagemCandidata.tornarPrincipal();
        repositorioImagem.salvar(imagemCandidata);

        return Resultado.sucesso("A imagem principal foi alterada com sucesso.");
    }
    
    /**
     * Verifica se as imagens são válidas.
     * 
     * @param imagens Imagens a serem validadas
     * @return Sucesso se as imagens forem válidas, falha caso contrário
     */
    private Resultado validarImagens(Collection<MultipartFile> imagens) {
        if (imagens.isEmpty()) {
            Resultado.erro("Informe pelo menos uma imagem");
        }
        
        for (MultipartFile imagem : imagens) {
            TipoMedia media = TipoMedia.determinar(imagem.getContentType());
            
            if (!Empresa.mediaSuportada(media)) {
                return Resultado.erro(MessageFormat.format(
                    "Formato não suportado. Apenas os formatos {0} são suportados.", 
                        Joiner.on(",").join(Empresa.mediasSuportadas()).toLowerCase()));
            }
            
            if (imagem.getSize() == 0) {
                return Resultado.erro(MessageFormat.format(
                    "Imagem vazia não é permitida: {0}.", imagem.getOriginalFilename()));
            }
        }
        
        return Resultado.sucesso();
    }
    
    /**
     * Armazena fisicamente uma imagem.
     * 
     * @param base Onde a imagem será armazenada
     * @param idImagem ID da imagem para armazenamento
     * @param imagem Imagem enviada
     */
    private void armazenarImagem(ImagemEmpresa imagem, MultipartFile arquivo) {
        try {
            BufferedImage imagemTemp = ImageIO.read(arquivo.getInputStream());
            BufferedImage novaImagem = Imagens.redimensionar(imagemTemp, 
                ImagemEmpresa.Dimensao.PEQUENA.getLargura(), 
                ImagemEmpresa.Dimensao.PEQUENA.getAltura());
            
            FSArquivo fsArquivo = imagem.criarArquivo();
            fsArquivo.alterarSufixo("p");
            fsSite.armazenar(novaImagem, fsArquivo);
            
            novaImagem = Imagens.redimensionar(imagemTemp, ImagemEmpresa.Dimensao.BUSCA.getLargura(), 
                ImagemEmpresa.Dimensao.BUSCA.getAltura());
            
            fsArquivo.alterarSufixo("b");
            fsSite.armazenar(novaImagem, fsArquivo);
            
            novaImagem = Imagens.redimensionar(imagemTemp, ImagemEmpresa.Dimensao.MEDIA.getLargura(), 
                ImagemEmpresa.Dimensao.MEDIA.getAltura());
            
            fsArquivo.alterarSufixo("m");
            fsSite.armazenar(novaImagem, fsArquivo);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
