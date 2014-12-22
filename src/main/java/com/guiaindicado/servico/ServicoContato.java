package com.guiaindicado.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.comando.geral.Contatar;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.email.ModeloEmail;
import com.guiaindicado.servico.email.EnviadorEmail;
import com.guiaindicado.servico.email.RenderizadorEmail;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações referentes ao contato.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class ServicoContato {

    @Autowired private EnviadorEmail enviadorEmail;
    @Autowired private RenderizadorEmail renderizadorEmail;
    
    @Value("#{environment['site.email.contato']}") String emailContato;
    
    /**
     * Envia para a fila de e-mails o e-mail de contato renderizado.
     * 
     * @param comando Dados do contato
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado contatar(Contatar comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro();
        }
        
        ModeloEmail modelo = ModeloEmail.criar(ModeloEmail.Tipo.CONTATO)
            .addPropriedade("nome", comando.getNome())
            .addPropriedade("email", comando.getEmail())
            .addPropriedade("telefone", comando.getTelefone())
            .addPropriedade("mensagem", comando.getMensagem());
            
        enviadorEmail.enviarParaFila(modelo.gerarEmail(emailContato, renderizadorEmail));
            
        return Resultado.sucesso(
            "Obrigado pelo seu contato, retornaremos para você em breve.");
    }
}
