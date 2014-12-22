package com.guiaindicado.servico;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.guiaindicado.comando.destaque.SalvarDestaque;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.geral.Destaque;
import com.guiaindicado.dominio.geral.RepositorioDestaque;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.item.RepositorioEmpresa;
import com.guiaindicado.notificacao.Notificador;
import com.guiaindicado.servico.fs.FSSite;
import com.guiaindicado.suporte.Imagens;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações pertinentes aos destaques do site. 
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class ServicoDestaque {

    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private RepositorioDestaque repositorioDestaque;
    @Autowired private FSSite fsSite;
    
    /**
     * Armazena temporariamente a imagem do destaque.
     * 
     * @param imagem Imagem a ser armazenada
     * @return Resultado com id da imagem temporária se sucesso, falha caso contrário
     */
    public Resultado armazenarTemp(MultipartFile imagem) {
        Preconditions.checkNotNull(imagem);
        
        TipoMedia media = TipoMedia.determinar(imagem.getContentType());
        
        if (!Destaque.mediaSuportada(media)) {
            return Resultado.erro(MessageFormat.format(
                "Formato não suportado. Apenas os formatos {0} são suportados.", 
                    Joiner.on(",").join(Destaque.mediasSuportadas()).toLowerCase()));
        }
        
        if (imagem.getSize() == 0) {
            return Resultado.erro("Imagem vazia não é permitida.");
        }
        
        try {
            String destaque = fsSite.armazenarTemp(imagem.getBytes());
            return Resultado.sucesso(MessageFormat.format(
                "Imagem armazenada temporariamente.", destaque), destaque);
        } catch (IOException e) {
            return Resultado.erro("Não foi possível armazenar a imagem. Tente novamente.");
        }
    }

    /**
     * Salva o destaque na base de dados e move a imagem através do repositório de imagem
     * e o ID da imagem para o diretório correto. Se a operação para gravação da imagem
     * falhar, o destaque não é salvo.
     * 
     * @param comando Dados do destaque
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado salvarDestaque(SalvarDestaque comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro();
        }
        
        Empresa empresa = repositorioEmpresa.getPorId(comando.getEmpresa());
        
        if (empresa == null) {
            Notificador.erro("empresa", "Empresa inválida");
            return Resultado.erro();
        }
        
        Destaque destaque = Objects.firstNonNull(
            repositorioDestaque.getPorId(comando.getId()), Destaque.criar());
        
        File imagemTemp = fsSite.getTemp(comando.getImagem());
        
        if (!destaque.temImagem() && !imagemTemp.exists()) {
            Notificador.erro("imagem", "Selecione uma imagem");
            return Resultado.erro();
        }
        
        destaque.setEmpresa(empresa)
            .setTitulo(comando.getTitulo())
            .setTexto(comando.getTexto())
            .setVertical(comando.getVertical())
            .setImagem(comando.getImagem());
        
        repositorioDestaque.salvar(destaque);
        
        if (imagemTemp.exists()) {
            try {
                BufferedImage imagem = ImageIO.read(imagemTemp);

                if (comando.getVertical()) {
                    imagem = Imagens.redimensionar(imagem, Destaque.Imagem.VERTICAL.getLargura(),
                        Destaque.Imagem.VERTICAL.getAltura());
                } else {
                    imagem = Imagens.redimensionar(imagem, Destaque.Imagem.HORIZONTAL.getLargura(),
                        Destaque.Imagem.HORIZONTAL.getAltura());
                }
  
                fsSite.armazenar(imagem, destaque.criarArquivo());
                fsSite.removerTemp(comando.getImagem());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        return Resultado.sucesso("Destaque salvo com sucesso.");
    }

    /**
     * Realiza a inativação do destaque. O destaque pode ser inativado apenas se a soma de
     * destaques ativos for maior ou igual a 5, sendo pelo menos 1 vertical e 4
     * horizontais.
     * 
     * @param id Id do destaque a ser inativado
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado inativarDestaque(int id) {
        Destaque destaque = repositorioDestaque.getPorId(id);
        
        if (destaque == null) {
            return Resultado.erro("Não foi possível inativar o destaque.");
        }
        
        int verticaisAtivos = repositorioDestaque.contarQuantidadeAtiva(true);
        int horizontaisAtivos = repositorioDestaque.contarQuantidadeAtiva(false);
        
        if (!destaque.inativavel(verticaisAtivos, horizontaisAtivos)) {
            return Resultado.erro("O destaque não pode ser inativado. A quantidade mínima de " +
                "destaques deve ser de 1 vertical e 4 horizontais.");
        }
        
        destaque.inativar();
        repositorioDestaque.salvar(destaque);
        
        return Resultado.sucesso("Destaque inativado com sucesso.");
    }
}
