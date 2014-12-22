package com.guiaindicado.servico.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.guiaindicado.dominio.email.Email;

/**
 * Classe responsável por delegar a entrega a seus respectivos processadores.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class EnviadorEmail {

    @Autowired private FilaEmail filaEmail;
    @Autowired private EntregadorEmail entregadorEmail;

    /**
     * Adiciona o e-mail a fila para entrega futura.
     * 
     * @param email E-mail a ser adicionado a fila
     */
    public void enviarParaFila(Email email) {
        filaEmail.enfileirar(email);
    }

    /**
     * Envia imediatamente um e-mail assincronamente através do entregador de e-mail.
     * 
     * @param email E-mail a ser enviado
     */
    @Async
    public void enviarAssincrono(Email email) {
        RegistroEntrega registro = entregadorEmail.entregar(email);

        if (!registro.houveSucesso()) {
            filaEmail.enfileirar(email);
        }
    }
}
