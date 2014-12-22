package com.guiaindicado.ui.controlador.admin;

import java.util.Collection;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.base.Optional;
import com.google.common.base.Predicates;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.comando.banner.SalvarBanner;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaBanner;
import com.guiaindicado.servico.ServicoBanner;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.ui.modelo.Banner;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes aos banners na área administrativa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorBanner extends ControladorAdminPadrao {

    @Autowired private ServicoBanner servicoBanner;
    @Autowired private PesquisaBanner pesquisaBanner;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a página para cadastro do banner.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/banners/cadastro", method = RequestMethod.GET)
    public String cadastro(Model modelo) {
        carregarPosicoes(modelo);
        carregarBase(modelo);
        
        return "admin/banners/cadastro";
    }
    
    /**
     * Inicia a página para alteração do banner.
     * 
     * @param id ID do banner
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/banners/{id}/alteracao", method = RequestMethod.GET)
    public String alteracao(@PathVariable("id") String id, Model modelo) {
        Optional<Banner> banner = pesquisaBanner.pesquisarPorId(NumberUtils.toInt(id));
        
        if (!banner.isPresent()) {
            MensagemDirecionada.set("Banner não encontrado.");
        }
        
        carregarPosicoes(modelo);
        carregarBase(modelo);
        
        modelo.addAttribute("banner", banner.get());
        return "admin/banners/cadastro";
    }
    
    /**
     * Popula o modelo com as posições dos banners.
     * 
     * @param modelo Modelo para a view
     */
    private void carregarPosicoes(Model modelo) {
        Collection<PosicaoBanner> posicoes = Lists.newArrayList(PosicaoBanner.values());
        Iterables.removeIf(posicoes, Predicates.equalTo(PosicaoBanner.INDEFINIDA));
        
        modelo.addAttribute("posicoes", posicoes);
    }
    
    /**
     * Inicia a página de lista de banners.
     * 
     * @param inicio Registro inicial da paginação
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/banners", method = RequestMethod.GET)
    public String lista(@RequestParam(value = "inicio", required = false) String inicio, Model modelo) {
        Pagina pagina = Pagina.criar(NumberUtils.toInt(inicio));
        modelo.addAttribute("resultado", pesquisaBanner.pesquisar(pagina));
        carregarBase(modelo);
        
        return "admin/banners";
    }
    
    /**
     * Salva o banner.
     * 
     * @param comando Dados do banner
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/banners/cadastro", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(SalvarBanner comando) {
        Resultado resultado = servicoBanner.salvarBanner(comando);
        
        if (resultado.houveSucesso()) {
            RespostaAcao resposta = RespostaAcao.sucesso(resultado);
            
            if (comando.getId() > 0) {
                resposta.redirecionar(redirecionamento.codificarAdmin("/banners"));
            }
            
            return resposta;
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Inativa o banner.
     * 
     * @param id ID do banner
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/banners/{id}/inativacao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(@PathVariable String id) {
        Resultado resultado = servicoBanner.inativarBanner(NumberUtils.toInt(id));
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Armazena temporariamente o banner.
     * 
     * @param imagem Banner a ser armazenado
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/banners/temp", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao armazenarBanner(@RequestParam("bannerTemp") MultipartFile imagem) {
        Resultado resultado = servicoBanner.armazenarTemp(imagem);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).addRetorno("banner", resultado.getRetorno());
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
