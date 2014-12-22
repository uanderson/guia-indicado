package com.guiaindicado.servico;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.io.Files;
import com.guiaindicado.comando.banner.SalvarBanner;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.geral.TipoMedia;
import com.guiaindicado.dominio.publicidade.Anunciante;
import com.guiaindicado.dominio.publicidade.Banner;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.dominio.publicidade.RepositorioAnunciante;
import com.guiaindicado.dominio.publicidade.RepositorioBanner;
import com.guiaindicado.notificacao.Notificador;
import com.guiaindicado.servico.fs.FSSite;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações referentes a banners.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class ServicoBanner {

    @Autowired private RepositorioAnunciante repositorioAnunciante;
    @Autowired private RepositorioBanner repositorioBanner;
    @Autowired private FSSite fsSite;
    
    /**
     * Armazena temporariamente o banner, mas, antes verifica se o tipo de media
     * {@link TipoMedia} é aceito como banner e o tamanho máximo permitido para um arquivo
     * de banner.
     * 
     * @param banner Banner a ser armazenado
     * @return Resultado com identificador temporário, falha caso contrário
     */
    public Resultado armazenarTemp(MultipartFile banner) {
        if (banner == null) {
            return Resultado.erro("Selecione um banner.");
        }

        if (banner.getSize() == 0) {
            return Resultado.erro("Banner com tamanho zerado não é permitido.");
        }
        
        if (!Banner.tamanhoPermitido(banner.getSize() / 1024)) {
            return Resultado.erro(MessageFormat.format(
                "O banner deve ter o tamanho máximo de {0}kb.", Banner.tamanhoPermitido()));
        }
        
        TipoMedia media = TipoMedia.determinar(banner.getContentType());
        
        if (!Banner.mediaSuportada(media)) {
            return Resultado.erro(MessageFormat.format(
                "Formato não suportado. Apenas os formatos {0} são suportados.", 
                    Joiner.on(",").join(Banner.mediasSuportadas()).toLowerCase()));
        }
        
        try {
            String bannerTemp = fsSite.armazenarTemp(banner.getBytes());
            return Resultado.sucesso(MessageFormat.format(
                "Banner armazenado temporariamente.", bannerTemp), bannerTemp);
        } catch (IOException e) {
            return Resultado.erro("Não foi possível armazenar o banner. Tente novamente.");
        }
    }
    
    /**
     * Salva o banner, porém, verifica a compatibilidade do {@link TipoMedia} e a dimensão
     * da imagem para a posição informada.
     * 
     * @param comando Dados do banner
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado salvarBanner(SalvarBanner comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro();
        }
        
        Anunciante anunciante = repositorioAnunciante.getPorId(comando.getAnunciante());
        
        if (anunciante == null) {
            Notificador.erro("anunciante", "Anunciante inválido");
            return Resultado.erro();            
        }
        
        File bannerTemp = fsSite.getTemp(comando.getBanner());
        TipoMedia tipoMedia = determinarTipoMedia(bannerTemp);
        PosicaoBanner posicao = PosicaoBanner.determinar(comando.getPosicao());
        
        if (!bannerTemp.exists() || TipoMedia.INDEFINIDO.equals(tipoMedia)) {
            Notificador.erro("banner", "Selecione um banner");
            return Resultado.erro();
        }
        
        if (!posicao.dimensaoSuportada(tipoMedia, bannerTemp)) {
            return Resultado.erro(MessageFormat.format(
                "Dimensão inválida. O banner deve ter {0}x{1} pixels.", 
                    posicao.getLargura(), posicao.getAltura()));
        }
        
        Banner banner = Objects.firstNonNull(
            repositorioBanner.getPorId(comando.getId()), Banner.criar());
        
        banner.setUrl(comando.getUrl())
            .setImagem(comando.getBanner())
            .setPosicao(posicao)
            .setTipoMedia(tipoMedia)
            .setAnunciante(anunciante);
        
        repositorioBanner.salvar(banner);
        
        try {
            fsSite.moverTemp(comando.getBanner(), banner.criarArquivo());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        
        return Resultado.sucesso("Banner salvo com sucesso.");
    }
    
    /**
     * Identifica o tipo de media do banner para o arquivo passado por parâmetro. O tipo será
     * {@code TipoMedia#INDEFINIDO} caso haja algum erro no processo de descobrimento.
     * 
     * @param arquivo Arquivo a ser identificado
     * @return Tipo de media do banner
     */
    private TipoMedia determinarTipoMedia(File arquivo) {
        InputStream entrada = null;
        
        try {
            entrada = new ByteArrayInputStream(Files.toByteArray(arquivo));
            String tipo = URLConnection.guessContentTypeFromStream(entrada);
            
            TipoMedia media = TipoMedia.determinar(tipo);
            
            if (TipoMedia.INDEFINIDO.equals(media)) {
                return TipoMedia.SWF;
            }
            
            return media;
        } catch (IOException ex) {
            return TipoMedia.INDEFINIDO;
        }
    }

    /**
     * Realiza a inativação do banner.
     * 
     * @param id ID do banner
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado inativarBanner(int id) {
        Banner banner = repositorioBanner.getPorId(id);
        
        if (banner == null) {
            return Resultado.erro("Não foi possível inativar o banner.");
        }
        
        banner.inativar();
        repositorioBanner.salvar(banner);
        
        return Resultado.sucesso("Banner inativado com sucesso.");
    }
}
