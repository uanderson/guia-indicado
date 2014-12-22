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
import com.guiaindicado.comando.anunciante.SalvarAnunciante;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.dominio.localizacao.RepositorioCidade;
import com.guiaindicado.dominio.publicidade.Anunciante;
import com.guiaindicado.dominio.publicidade.RepositorioAnunciante;
import com.guiaindicado.notificacao.Notificador;
import com.guiaindicado.servico.fs.FSSite;
import com.guiaindicado.suporte.Imagens;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações referentes a anunciantes.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class ServicoAnunciante {

    @Autowired private RepositorioAnunciante repositorioAnunciante;
    @Autowired private RepositorioCidade repositorioCidade;
    @Autowired private FSSite fsSite;

    /**
     * Armazena temporariamente a imagem do anunciante.
     * 
     * @param imagem Imagem a ser armazenada
     * @return Resultado com identificador da imagem temporária se sucesso, falha caso contrário
     */
    public Resultado armazenarTemp(MultipartFile imagem) {
        Preconditions.checkNotNull(imagem);
        
        TipoMedia media = TipoMedia.determinar(imagem.getContentType());
        
        if (!Anunciante.mediaSuportada(media)) {
            return Resultado.erro(MessageFormat.format(
                "Formato não suportado. Apenas os formatos {0} são suportados.", 
                    Joiner.on(",").join(Anunciante.mediasSuportadas()).toLowerCase()));
        }
        
        if (imagem.getSize() == 0) {
            return Resultado.erro("Imagem vazia não é permitida.");
        }
        
        try {
            String idImagem = fsSite.armazenarTemp(imagem.getBytes());
            return Resultado.sucesso(MessageFormat.format(
                "Imagem armazenada temporariamente.", idImagem), idImagem);
        } catch (IOException e) {
            return Resultado.erro("Não foi possível armazenar a imagem. Tente novamente.");
        }
    }
    
    /**
     * Salva o anunciante.
     * 
     * @param comando Dados do anunciante
     * @return Sucesso ou falha da ação
     */
    @Transactional
    public Resultado salvarAnunciante(SalvarAnunciante comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro();
        }
        
        Anunciante anunciante = Objects.firstNonNull(
            repositorioAnunciante.getPorId(comando.getId()), Anunciante.criar());
      
        File imagemTemp = fsSite.getTemp(comando.getImagem());
        
        if (!anunciante.temImagem() && !imagemTemp.exists()) {
            Notificador.erro("imagem", "Selecione uma imagem");
            return Resultado.erro();
        }

        Cidade cidade = repositorioCidade.getPorId(comando.getCidade());

        anunciante.setNome(comando.getNome())
            .setImagem(comando.getImagem())
            .setCidade(cidade)
            .setTelefone(comando.getTelefone())
            .setEmail(comando.getEmail())
            .setSite(comando.getSite());
        
        repositorioAnunciante.salvar(anunciante);
        
        if (imagemTemp.exists()) {
            try {
                BufferedImage imagem = Imagens.redimensionar(ImageIO.read(imagemTemp), 
                    Anunciante.Imagem.PADRAO.getLargura(),
                    Anunciante.Imagem.PADRAO.getAltura());
                
                fsSite.armazenar(imagem, anunciante.criarArquivo());
                fsSite.removerTemp(comando.getImagem());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        
        return Resultado.sucesso("Anunciante salvo com sucesso.");
    }
}
