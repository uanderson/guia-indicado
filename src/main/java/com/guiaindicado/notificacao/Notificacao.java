package com.guiaindicado.notificacao;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.base.Preconditions;

/**
 * Classe que representa uma notificação utilizada pelo {@link Notificador}.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class Notificacao {

    private String origem;
    private String mensagem;

    /**
     * Cria uma notificação com mensagem sem origem.
     * 
     * @param mensagem Mensagem de notificação
     */
    private Notificacao(String mensagem) {
        this("", mensagem);
    }

    /**
     * Cria uma notificação com origem e mensagem.
     * 
     * @param origem Quem gerou a notificação
     * @param mensagem Mensagem de notificação
     */
    private Notificacao(String origem, String mensagem) {
        this.origem = Preconditions.checkNotNull(origem);
        this.mensagem = Preconditions.checkNotNull(mensagem);
    }

    /**
     * @see #Notificacao(String)
     */
    public static Notificacao of(String mensagem) {
        return of("", mensagem);
    }

    /**
     * @see #Notificacao(String, String)
     */
    public static Notificacao of(String origem, String mensagem) {
        return new Notificacao(origem, mensagem);
    }

    public String getOrigem() {
        return origem;
    }

    public String getMensagem() {
        return mensagem;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("origem", origem)
            .append("mensagem", mensagem)
            .toString();
    }
}
