package com.guiaindicado.ui.controlador.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.servico.indice.IndexadorBusca;
import com.guiaindicado.servico.indice.IndexadorCidade;
import com.guiaindicado.ui.suporte.RespostaAcao;

/**
 * Processa as requisições para operações de índice.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorIndice extends ControladorAdminPadrao {

    @Autowired private IndexadorBusca servicoIndiceBusca;
    @Autowired private IndexadorCidade servicoIndiceCidade;

    /**
     * Inicia a tela de índices.
     */
    @RequestMapping(value = "/admin/indices", method = RequestMethod.GET)
    public String indice(Model modelo) {
        carregarBase(modelo);
        return "admin/indices";
    }

    /**
     * Indexa todas as empresas.
     * 
     * @param comando Dados da indexação
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/indices/busca", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao indexarBusca() {
        Resultado resultado = servicoIndiceBusca.indexarBusca();

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }

    /**
     * Indexa todas as cidades.
     * 
     * @param comando Dados da indexação
     * @return Sucesso ou falha da operação
     */
    @RequestMapping(value = "/admin/indices/cidade", method = RequestMethod.POST)
    public @ResponseBody RespostaAcao indexarCidade() {
        Resultado resultado = servicoIndiceCidade.indexarCidade();

        if (resultado.houveSucesso()) {
            return RespostaAcao.sucesso(resultado);
        } else {
            return RespostaAcao.erro(resultado);
        }
    }
}
