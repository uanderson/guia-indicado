package com.guiaindicado.ui.controlador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Throwables;
import com.guiaindicado.ui.suporte.MensagemDirecionada;

/**
 * Controlador padrão para os controladores administrativos e do site. Sua principal
 * função é dar base e capturar erros gerados pelos controladores filhos, centralizando o
 * log e a notificação. Trata também o sistema de mensagem direcionada.
 * 
 * @author Uanderson Soares Gonçalves
 */
public abstract class ControladorBase {

    private static final Logger LOG = LoggerFactory.getLogger(ControladorBase.class);
    
    /**
     * Loga as exceções ocorridas na aplicação e notifica os administradores.
     * 
     * @param excecao Exceção lançada
     */
    @ExceptionHandler(Throwable.class)
    public @ResponseBody void resolverExcecao(Throwable excecao) {
        LOG.error(excecao.getMessage(), excecao);
        throw Throwables.propagate(excecao);
    }
    
    /**
     * Recupera a mensagem direcionada corrente e adiciona no modelo.
     * 
     * @return Mensagem direcionada
     */
    @ModelAttribute("mensagemDirecionada")
    public String getMensagemDirecionada() {
        return MensagemDirecionada.get();
    }
}
