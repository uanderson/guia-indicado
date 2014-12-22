package com.guiaindicado.servico.email;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.dominio.email.Email;
import com.guiaindicado.dominio.email.RepositorioEmail;

/**
 * Processa e mantém a fila de e-mail da aplicação. 
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class FilaEmail {

    @Autowired private RepositorioEmail repositorioEmail;
    @Autowired private EntregadorEmail entregadorEmail;

    /**
     * Adiciona um e-mail a fila no banco de dados.
     * 
     * @param email E-mail a ser enviado
     */
    @Transactional
    public void enfileirar(Email email) {
        repositorioEmail.salvar(email);
    }

    /**
     * Processa todos os e-mails da fila enviando-os através do entregador de e-mail.
     */
    @Transactional
    public void processar() {
        Collection<Email> emailsPendentes = 
            repositorioEmail.listarPorStatus(Email.Status.PENDENTE);

        if (emailsPendentes.isEmpty()) {
            return;
        }
        
        RegistroEntrega registro = entregadorEmail.entregar(emailsPendentes);
        
        if (!registro.houveSucesso()) {
            emailsPendentes.removeAll(registro.getFalhaEnvio());
        }
        
        for (Email email : emailsPendentes) {
            email.enviar();
            repositorioEmail.salvar(email);
        }
    }

    /**
     * Remove todos os e-mails que já foram enviados da fila.
     */
    @Transactional
    public void limpar() {
        repositorioEmail.removerPorStatus(Email.Status.ENVIADO);
    }
}
