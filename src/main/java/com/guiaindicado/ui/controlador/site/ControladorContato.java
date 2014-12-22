package com.guiaindicado.ui.controlador.site;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.geral.Contatar;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.servico.ServicoContato;
import com.guiaindicado.ui.suporte.Requisicao;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes ao contato.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorContato extends ControladorSitePadrao {

    @Autowired private ServicoContato servicoContato;
    
    /**
     * Inicia tela de contato.
     * 
     * @param requisicao Requisição corrente. 
     * @return Nome da view
     */
    @RequestMapping(value = "/contato", method = RequestMethod.GET)
    public String contato(HttpServletRequest requisicao, Model modelo) {
        if (!Requisicao.requisicaoAjax(requisicao)) {
            return "redirect:/";
        }
        
        carregarBase(modelo);
        return "contato";
    }
    
    /**
     * Envia e-mail de contato.
     * 
     * @param contatar Dados do contato
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/contatar", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao contatar(Contatar contatar) {
        Resultado resultado = servicoContato.contatar(contatar);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
