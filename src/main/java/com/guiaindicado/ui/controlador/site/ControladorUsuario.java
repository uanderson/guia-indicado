package com.guiaindicado.ui.controlador.site;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Optional;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.AlterarSenha;
import com.guiaindicado.comando.usuario.AlterarUsuario;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaUsuario;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoUsuario;
import com.guiaindicado.ui.modelo.Usuario;
import com.guiaindicado.ui.suporte.Requisicao;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa requisições referentes ao usuário.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorUsuario extends ControladorSitePadrao {

    @Autowired private ServicoUsuario servicoUsuario;
    @Autowired private PesquisaUsuario pesquisaUsuario;

    /**
     * Inicia a página de alteração dos dados do usuário.
     * 
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/usuario/alteracao", method = RequestMethod.GET)
    public String alteracaoDados(UsuarioSite usuarioSite, Model modelo) {
        Optional<Usuario> usuario = pesquisaUsuario.pesquisarPorEmail(usuarioSite.getUsername());
        modelo.addAttribute("usuario", usuario.get());
        
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO);
        return "usuario/alteracao";
    }

    /**
     * Salva os dados do usuário.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados para alteração
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/usuario/alteracao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao alterarDados(UsuarioSite usuarioSite, AlterarUsuario comando) {
        Resultado resultado = servicoUsuario.alterarUsuario(usuarioSite, comando);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).recarregar();
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Inicia a tela de alteração de senha.
     * 
     * @return Nome da view
     */
    @RequestMapping(value = "/senha/alteracao", method = RequestMethod.GET)
    public String alteracaoSenha(Model modelo, HttpServletRequest requisicao) {
        if (!Requisicao.requisicaoAjax(requisicao)) {
            return "redirect:/";
        }
        
        carregarBase(modelo);
        return "senha/alteracao";
    }
    
    /**
     * Realiza a alteração da senha do usuário.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados para alteração
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/senha/alteracao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao alterarSenha(UsuarioSite usuarioSite, AlterarSenha comando) {
        Resultado resultado = servicoUsuario.alterarSenha(usuarioSite, comando);

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
