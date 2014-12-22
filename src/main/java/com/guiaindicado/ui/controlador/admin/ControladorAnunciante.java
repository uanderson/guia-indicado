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
import com.guiaindicado.comando.anunciante.SalvarAnunciante;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.pesquisa.PesquisaAnunciante;
import com.guiaindicado.servico.ServicoAnunciante;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.ui.modelo.Anunciante;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes a anunciantes na parte administrativa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorAnunciante extends ControladorAdminPadrao {
    
    @Autowired private ServicoAnunciante servicoAnunciante;
    @Autowired private PesquisaAnunciante pesquisaAnunciante;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a página de lista de anunciantes.
     * 
     * @param modelo Modelo para a view
     * @return Coleção de anunciantes
     */
    @RequestMapping(value = "/admin/anunciantes", method = RequestMethod.GET)
    public String lista(@RequestParam(value = "inicio", required = false) String inicio, Model modelo) {
        Pagina pagina = Pagina.criar(NumberUtils.toInt(inicio));
        modelo.addAttribute("resultado", pesquisaAnunciante.pesquisar(pagina));
        carregarBase(modelo);
        
        return "admin/anunciantes";
    }
    
    /**
     * Inicia a página para cadastro do anunciante.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/anunciantes/cadastro", method = RequestMethod.GET)
    public String cadastrar(Model modelo) {
        modelo.addAttribute("anunciante", new Anunciante());
        carregarBase(modelo);
        
        return "admin/anunciantes/cadastro";
    }
    
    /**
     * Inicia a alteração do anunciante.
     * 
     * @param id ID do anunciante
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/anunciantes/{id}/alteracao", method = RequestMethod.GET)
    public String alterar(@PathVariable String id, Model modelo) {
        Optional<Anunciante> anunciante = pesquisaAnunciante.pesquisarPorId(NumberUtils.toInt(id));
        
        if (!anunciante.isPresent()) {
            MensagemDirecionada.set("Anunciante não encontrado.");
            modelo.addAttribute("anunciante", new Anunciante());
        } else {
            modelo.addAttribute("anunciante", anunciante.get());
        }
        
        carregarBase(modelo);
        return "admin/anunciantes/cadastro";
    }
    
    /**
     * Salva o anunciante.
     * 
     * @param comando Dados do anunciante
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/anunciantes/cadastro", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(SalvarAnunciante comando) {
        Resultado resultado = servicoAnunciante.salvarAnunciante(comando);

        if (resultado.houveSucesso()) {
            RespostaAcao resposta = RespostaAcao.sucesso(resultado);
            
            if (comando.getId() > 0) {
                resposta.redirecionar(redirecionamento.codificarAdmin("/anunciantes"));
            }
            
            return resposta;
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Armazena temporariamente a imagem do anunciante.
     * 
     * @param imagem Imagem a ser armazenada
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/anunciantes/temp", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao armazenarImagem(@RequestParam("imagemTemp") MultipartFile imagem) {
        Resultado resultado = servicoAnunciante.armazenarTemp(imagem);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).addRetorno("imagem", resultado.getRetorno());
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Pesquisa por anunciantes pelo nome.
     * 
     * @param nome Nome do anunciante
     * @return Coleção de anunciantes
     */
    @RequestMapping(value = "/admin/anunciantes/pesquisa.json", method = RequestMethod.GET)
    public @ResponseBody Collection<Anunciante> pesquisar(
            @RequestParam(value="termo", required = false) String nome) {
        return pesquisaAnunciante.pesquisarPorNome(nome);
    }
}
