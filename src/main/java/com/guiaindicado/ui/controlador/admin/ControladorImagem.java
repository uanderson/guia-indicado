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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Optional;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.pesquisa.PesquisaEmpresa;
import com.guiaindicado.pesquisa.PesquisaImagem;
import com.guiaindicado.servico.ServicoEmpresa;
import com.guiaindicado.servico.ServicoImagem;
import com.guiaindicado.ui.modelo.Empresa;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes as imagens das empresas.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorImagem extends ControladorAdminPadrao {
    
    @Autowired private PesquisaEmpresa pesquisaEmpresa;
    @Autowired private PesquisaImagem pesquisaImagem;
    @Autowired private ServicoEmpresa servicoEmpresa;
    @Autowired private ServicoImagem servicoImagem;
    
    /**
     * Inicia a página de imagens.
     * 
     * @param id ID da empresa
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/empresas/{id}/imagens", method = RequestMethod.GET)
    public String selecao(@PathVariable String id, Model modelo) {
        int idEmpresa = NumberUtils.toInt(id);
        Optional<Empresa> empresa = pesquisaEmpresa.pesquisarPorId(idEmpresa);
        carregarBase(modelo);
        
        if (!empresa.isPresent()) {
            MensagemDirecionada.set("Empresa não encontrada");
            return "admin/empresas";
        }
            
        modelo.addAttribute("empresa", empresa.get());
        modelo.addAttribute("imagens", pesquisaImagem.pesquisarPorEmpresa(idEmpresa));
        
        return "admin/empresas/imagens";
    }
    
    /**
     * Armazena as imagens enviadas para a empresa informada.
     * 
     * @param id ID da empresa
     * @param imagens Imagens a serem armazenadas
     * @param redirecionamento Modelo de redirecionamento
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/empresas/{id}/imagens", method = RequestMethod.POST)
    public String enviar(
            @RequestParam("imagem") Collection<MultipartFile> imagens,
            @PathVariable String id, RedirectAttributes redirecionamento) {
        
        int empresa =  NumberUtils.toInt(id);
        Resultado resultado = servicoImagem.armazenarImagensEmpresa(empresa, imagens);
        MensagemDirecionada.set(resultado.getMensagem());
        
        redirecionamento.addAttribute("id", id);
        return "redirect:/admin/empresas/{id}/imagens";
    }
    
    /**
     * Define uma imagem como principal.
     * 
     * @param id ID da imagem
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/imagens/{id}/principal", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao principal(@PathVariable String id) {
        int idImagem =  NumberUtils.toInt(id);
        Resultado resultado = servicoImagem.tornarPrincipal(idImagem);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Excluí uma imagem.
     * 
     * @param id ID da imagem
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/imagens/{id}/exclusao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao exclusao(@PathVariable String id) {
        int idImagem =  NumberUtils.toInt(id);
        Resultado resultado = servicoImagem.excluirImagem(idImagem);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
