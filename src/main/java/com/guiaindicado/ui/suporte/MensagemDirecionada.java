package com.guiaindicado.ui.suporte;

import javax.servlet.http.HttpSession;

import com.google.common.base.Preconditions;

/**
 * Responsável por manter a mensagem colocada na sessão para um redirecionamento.
 * 
 * @author Uanderson Soares Gonçalves
 */
public final class MensagemDirecionada {

    /**
     * Atributo com a mensagem na sessão.
     */
    public static final String ATTRIBUTO_MENSAGEM_DIRECIONADA = "mensagemDirecionada";

    /**
     * Seta na sessão a mensagem a ser obtida após o redirecionamento.
     * 
     * @param mensagem Mensagem a ser notificada
     */
    public static void set(String mensagem) {
        HttpSession sessao = Requisicao.get().getSession();
        sessao.setAttribute(ATTRIBUTO_MENSAGEM_DIRECIONADA, 
            Preconditions.checkNotNull(mensagem, "Mensagem é obrigatória"));
    }

    /**
     * Obtém da sessão a mensagem direcionada caso ela exista. No entanto, a mensagem só é
     * obtida se a requisição nã for ajax. Outro ponto é que se a mensagem existir na
     * sessão, ela é removida durante a obtenção da mesma.
     * 
     * @return Mensagem direcionada caso exista
     */
    public static String get() {
        HttpSession sessao = Requisicao.get().getSession(false);
        
        if (sessao == null) {
            return null;
        }
        
        String mensagem = (String) sessao.getAttribute(ATTRIBUTO_MENSAGEM_DIRECIONADA);
        
        if (!Requisicao.requisicaoAjax()) {
            sessao.removeAttribute(ATTRIBUTO_MENSAGEM_DIRECIONADA);
        }

        return mensagem;
    }
}
