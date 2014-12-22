
package com.guiaindicado.ui.controlador.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.empresa.IndicarEmpresa;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.ServicoEmpresa;
import com.guiaindicado.ui.suporte.Redirecionamento;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições referentes a indicação da empresa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorIndicacaoEmpresa extends ControladorSitePadrao {
    
    @Autowired private ServicoEmpresa servicoEmpresa;
    @Autowired private Redirecionamento redirecionamento;
    
    /**
     * Inicia a página para indicação de empresa.
     *
     * @param modelo Modelo para a view
     * @return Nome da view
     */
    @RequestMapping(value = "/empresa/indicacao", method = RequestMethod.GET)
    public String indicacao(Model modelo) {
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO);
        return "empresa/indicacao";
    }
    
    /**
     * Realiza a indicação da empresa.
     * 
     * @param comando Dados da empresa
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/empresa/indicacao", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao indicar(UsuarioSite usuarioSite, IndicarEmpresa comando) {
        Resultado resultado = servicoEmpresa.indicarEmpresa(usuarioSite, comando);
        
        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado).redirecionar(redirecionamento.codificarSite("/"));
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
