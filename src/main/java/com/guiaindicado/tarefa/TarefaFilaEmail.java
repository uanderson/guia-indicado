package com.guiaindicado.tarefa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.guiaindicado.servico.email.FilaEmail;

/**
 * Tarefa agendada para processamento da fila de e-mail.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class TarefaFilaEmail {

    @Autowired private FilaEmail filaEmail;

    /**
     * Envia todos os e-mails pendentes na fila.
     */
//    @Scheduled(cron = "0 0/30 * * * *")
    @Scheduled(fixedDelay = 1000)
    public void enviarFila() {
        filaEmail.processar();
    }

    /**
     * Remove da fila todos os e-mails já enviados.
     */
    @Scheduled(cron = "0 0 23 * * *")
    public void limparFila() {
        filaEmail.limpar();
    }
}
