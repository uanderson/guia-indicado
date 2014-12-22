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

import com.google.common.base.Optional;
import com.guiaindicado.comando.empresa.SalvarEmpresa;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.pesquisa.PesquisaCategoria;
import com.guiaindicado.pesquisa.PesquisaEmpresa;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoEmpresa;
import com.guiaindicado.suporte.Pagina;
import com.guiaindicado.ui.modelo.Empresa;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes a empresa na parte administrativa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorEmpresa extends ControladorAdminPadrao {
    
    @Autowired private ServicoEmpresa servicoEmpresa;
    @Autowired private PesquisaEmpresa pesquisaEmpresa;
    @Autowired private PesquisaCategoria pesquisaCategoria;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a página de listagem das empresas.
     * 
     * @param inicio Primeiro registro da página
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/empresas", method = RequestMethod.GET)
    public String lista(@RequestParam(value = "inicio", required = false) String inicio, Model modelo) {
        Pagina pagina = Pagina.criar(NumberUtils.toInt(inicio), 15);
        modelo.addAttribute("resultado", pesquisaEmpresa.pesquisar(pagina));
        carregarBase(modelo);
        
        return "admin/empresas";
    }
    
    /**
     * Inicia a página para cadastro da empresa.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/empresas/cadastro", method = RequestMethod.GET)
    public String cadastro(Model modelo) {
        modelo.addAttribute("empresa", new Empresa());
        carregarBase(modelo);
        
        return "admin/empresas/cadastro";
    }
    
    /**
     * Inicia a página para alteração da empresa.
     * 
     * @param id ID da empresa
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/admin/empresas/{id}/alteracao", method = RequestMethod.GET)
    public String alteracao(@PathVariable String id, Model modelo) {
        Optional<Empresa> empresa = pesquisaEmpresa.pesquisarPorId(NumberUtils.toInt(id));
        
        if (!empresa.isPresent()) {
            MensagemDirecionada.set("Empresa não encontrada.");
            modelo.addAttribute("empresa", new Empresa());
        } else {
            modelo.addAttribute("empresa", empresa.get());
        }
        
        carregarBase(modelo);
        return "admin/empresas/cadastro";
    }
    
    /**
     * Salva a empresa.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados da empresa
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/empresas/cadastro", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao salvar(UsuarioSite usuarioSite, SalvarEmpresa comando) {
        Resultado resultado = servicoEmpresa.salvarEmpresa(usuarioSite, comando);

        if (resultado.houveSucesso()) {
            RespostaAcao resposta = RespostaAcao.sucesso(resultado);
            
            if (comando.getId() > 0) {
                resposta.redirecionar(redirecionamento.codificarAdmin("/empresas"));
            }
            
            return resposta;
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Modera uma empresa liberando-a ao site.
     * 
     * @param comando ID da empresa
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/empresas/{id}/moderacao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao moderar(@PathVariable String id) {
        Resultado resultado = servicoEmpresa.moderarEmpresa(NumberUtils.toInt(id));

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Pesquisa as empresas por nome.
     * 
     * @param nome Nome da empresa
     * @return Coleção de empresas
     */
    @RequestMapping(value = "/admin/empresas/pesquisa.json", method = RequestMethod.GET)
    public @ResponseBody Collection<Empresa> pesquisar(
            @RequestParam(value="termo", required = false) String nome) {
        return pesquisaEmpresa.pesquisarPorNome(nome);
    }
}
