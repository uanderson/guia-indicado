package com.guiaindicado.ui.controlador.site;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.empresa.AvaliarEmpresa;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoAvaliacao;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes a avaliação de empresas/produtos e serviços.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorAvaliacao extends ControladorBuscaPadrao {
    
    @Autowired private ServicoAvaliacao servicoAvaliacao;
    
    /**
     * Avalia uma empresa.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados para a avaliação
     * @param requisicao Requisição corrente
     * @param modelo Modelo para a view
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/empresa/{empresa}/avaliacao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao avaliarEmpresa(UsuarioSite usuarioSite, 
        AvaliarEmpresa comando, HttpServletRequest requisicao, Model modelo) {
      
        comando.setIp(requisicao.getRemoteAddr());
        Resultado resultado = servicoAvaliacao.avaliarEmpresa(usuarioSite, comando);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
