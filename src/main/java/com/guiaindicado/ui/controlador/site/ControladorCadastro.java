package com.guiaindicado.ui.controlador.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.CadastrarUsuario;
import com.guiaindicado.comando.usuario.ConfirmarCadastro;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoCadastro;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes ao cadastro no usuário no site.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorCadastro extends ControladorSitePadrao {

    @Autowired private ServicoCadastro servicoCadastro;
    @Autowired private Redirecionamento redirecionamento;

    /**
     * Inicia o cadastro do usuário. Caso o usuário já esteja logado, envia a home.
     * 
     * @param usuarioSite Usuário ativo
     * @param modelo Modelo para a view 
     * @return nome da view
     */
    @RequestMapping(value = "/cadastro", method = RequestMethod.GET)
    public String cadastro(Model model, UsuarioSite usuarioSite, Model modelo) {
        if (usuarioSite.isLogado()) {
            return "redirect:/";
        } else {
            carregarBase(modelo);
            carregarBanners(modelo, PosicaoBanner.TOPO);
            return "cadastro";
        }
    }

    /**
     * Cadastra o usuário.
     * 
     * @param comando Dados do usuário
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/cadastro", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao cadastrar(CadastrarUsuario comando) {
        Resultado resultado = servicoCadastro.cadastrarUsuario(comando);

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado)
                .redirecionar(redirecionamento.codificarSite("/"));
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Realiza confirmação do cadastro do usuário no site.
     * 
     * @param usuario Usuário ativo
     * @param comando Dados de confirmação
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/cadastro/{token}/confirmacao", method = RequestMethod.GET)
    public String confirmarCadastro(UsuarioSite usuario, ConfirmarCadastro comando) {
        Resultado resultado = servicoCadastro.confirmarCadastro(usuario, comando);

        if (resultado.houveSucesso()) {
            MensagemDirecionada.set(resultado.getMensagem());
            return "redirect:/login";
        } else {
            MensagemDirecionada.set(resultado.getMensagem());
            return "redirect:/";
        }
    }
}
