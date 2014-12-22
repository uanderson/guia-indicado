package com.guiaindicado.ui.controlador.site;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.ReiniciarSenha;
import com.guiaindicado.comando.usuario.SolicitarReinicioSenha;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoEsqueciSenha;
import com.guiaindicado.ui.suporte.MensagemDirecionada;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.Requisicao;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes ao esquecimento de senha.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorEsqueciSenha extends ControladorSitePadrao {

    @Autowired private ServicoEsqueciSenha servicoEsqueciSenha;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a tela de reset de senha
     * 
     * @param requisicao Requisição corrente
     * @return Nome da view
     */
    @RequestMapping(value ="/senha/esqueci", method = RequestMethod.GET)
    public String esqueci(HttpServletRequest requisicao, Model modelo) {
        if (!Requisicao.requisicaoAjax(requisicao)) {
            return "redirect:/";
        }
        
        carregarBase(modelo);
        return "senha/esqueci";
    }
    
    /**
     * Solicita o reset de senha.
     * 
     * @param comando Dados para solicitação
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/senha/esqueci", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao solicitar(SolicitarReinicioSenha comando) {
        Resultado resultado = servicoEsqueciSenha.solicitarReinicioSenha(comando);

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
    
    /**
     * Processa e verifica o token do usuário. Caso haja sucesso, envia-o para alterar sua senha,
     * caso contrário, envia para a home.
     * 
     * @param usuarioSite Usuário ativo
     * @param token Token para validação
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/senha/{token}/esqueci", method = RequestMethod.GET)
    public String alterarSenha(UsuarioSite usuarioSite, @PathVariable String token, Model modelo) {
        Resultado resultado = servicoEsqueciSenha.verificarToken(usuarioSite, token);

        if (!resultado.houveSucesso()) {
            MensagemDirecionada.set(resultado.getMensagem());
            return "redirect:/";
        }

        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO);
        return "senha/esqueci/alteracao";
    }
    
    /**
     * Salva a nova senha do usuário. Caso haja sucesso, envia o usuário a tela de login.
     * 
     * @param comando Dados para reinicio
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/senha/reiniciar", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao reiniciarSenha(ReiniciarSenha comando) {
        Resultado resultado = servicoEsqueciSenha.reiniciarSenha(comando);

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado)
                .redirecionar(redirecionamento.codificarSite("/login"));
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
