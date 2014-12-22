package com.guiaindicado.ui.controlador.admin;

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
import com.guiaindicado.comando.destaque.SalvarDestaque;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.pesquisa.PesquisaDestaque;
import com.guiaindicado.servico.ServicoDestaque;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.ui.modelo.Destaque;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes aos destaques.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorDestaque extends ControladorAdminPadrao {

    @Autowired private ServicoDestaque servicoDestaque;
    @Autowired private PesquisaDestaque pesquisaDestaque;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a página de cadastro.
     * 
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/destaques/cadastro", method = RequestMethod.GET)
    public String cadastro(Model modelo) {
        carregarBase(modelo);
        return "admin/destaques/cadastro";
    }
    
    /**
     * Inicia a página para alteração do destaque
     * 
     * @param id ID do destaque
     * @param modelo Modelo para view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/destaques/{id}/alteracao", method = RequestMethod.GET)
    public String alteracao(@PathVariable("id") String id, Model modelo) {
        Optional<Destaque> destaque = pesquisaDestaque.pesquisarPorId(NumberUtils.toInt(id));
        
        if (!destaque.isPresent()) {
            MensagemDirecionada.set("Destaque não encontrado.");
        }
        
        carregarBase(modelo);
        modelo.addAttribute("destaque", destaque.get());
        return "admin/destaques/cadastro";
    }
 
    /**
     * Inicia a tela de lista de destaques.
     *  
     * @param inicio Primeiro registro da página
     * @param modelo Modelo para view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/destaques", method = RequestMethod.GET)
    public String lista(@RequestParam(value = "inicio", required = false) String inicio, Model modelo) {
        Pagina pagina = Pagina.criar(NumberUtils.toInt(inicio));
        modelo.addAttribute("resultado", pesquisaDestaque.pesquisar(pagina));
        carregarBase(modelo);
        
        return "admin/destaques";
    }
    
    /**
     * Salva o destaque.
     * 
     * @param comando Dados do destaque
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/destaques/cadastro", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(SalvarDestaque comando) {
        Resultado resultado = servicoDestaque.salvarDestaque(comando);
        
        if (resultado.houveSucesso()) {
            RespostaAcao resposta = RespostaAcao.sucesso(resultado);
            
            if (comando.getId() > 0) {
                resposta.redirecionar(redirecionamento.codificarAdmin("/destaques"));
            }
            
            return resposta;
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Inativa o destaque.
     * 
     * @param id ID do destaque
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/destaques/{id}/inativacao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(@PathVariable String id) {
        Resultado resultado = servicoDestaque.inativarDestaque(NumberUtils.toInt(id));
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Armazena temporariamente a imagem do destaque.
     * 
     * @param vertical Indica se a imagem é vertical
     * @param imagem Imagem a ser armazenada
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/destaques/temp", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao armazenarImagem(@RequestParam("imagemTemp") MultipartFile imagem) {
        Resultado resultado = servicoDestaque.armazenarTemp(imagem);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).addRetorno("imagem", resultado.getRetorno());
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
