package com.guiaindicado.ui.suporte;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.notificacao.Notificacao;
import com.guiaindicado.notificacao.Notificador;

/**
 * Mantém o status da resposta de uma ação executada através de AJAX. A classe retém dados como
 * notificações de erro, mensagem, status e dados extras de retorno.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class RespostaAcao {

    private Status status;
    private String mensagem;
    private String redirecionar;
    private String recarregar;
    private Map<String, Object> retorno = Maps.newHashMap();
    private Collection<Notificacao> notificacoes = Lists.newArrayList();

    /**
     * Cria uma resposta.
     * 
     * @param status Status da resposta
     * @param mensagem Mensagem baseada no status
     */
    private RespostaAcao(Status status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
        this.redirecionar = "";
        this.recarregar = "";

        notificacoes.addAll(Notificador.getNotificacoes());
    }

    /**
     * Cria uma resposta de sucesso baseado no {@link Resultado}.
     * 
     * @param resultado {@link Resultado}
     * @return Nova resposta
     */
    public static RespostaAcao sucesso(Resultado resultado) {
        return new RespostaAcao(Status.SUCESSO, resultado.getMensagem());
    }

    /**
     * Cria uma resposta de erro baseado no {@link Resultado}.
     * 
     * @param resultado {@link Resultado}
     * @return Nova resposta
     */
    public static RespostaAcao erro(Resultado resultado) {
        return new RespostaAcao(Status.ERRO, resultado.getMensagem());
    }

    /**
     * Define a mensagem da resposta.
     * 
     * @param mensagem Mensagem da resposta
     * @return Resposta alterada
     */
    public RespostaAcao mensagem(String mensagem) {
        this.mensagem = Preconditions.checkNotNull(mensagem);
        return this;
    }

    /**
     * Define que a interface deverá ser recarregada.
     * 
     * @return Resposta alterada
     */
    public RespostaAcao recarregar() {
        this.recarregar = "true";
        MensagemDirecionada.set(mensagem);
        mensagem = "";
        return this;
    }

    /**
     * Define que a interface deverá redirecionar para a URL.
     * 
     * @param url Destino do redirecionamento
     * @return Resposta alterada
     */
    public RespostaAcao redirecionar(String url) {
        this.redirecionar = Preconditions.checkNotNull(url);
        MensagemDirecionada.set(mensagem);
        mensagem = "";
        return this;
    }

    /**
     * Adiciona um objeto para ser serializado na resposta.
     * 
     * @param nome Nome da propriedade de retorno
     * @param valor Valor de retorno
     * @return Resposta alterada
     */
    public RespostaAcao addRetorno(String nome, Object valor) {
        Preconditions.checkNotNull(nome);
        Preconditions.checkNotNull(valor);
        
        this.retorno.put(nome, valor);
        return this;
    }

    public Status getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    @JsonInclude(Include.NON_EMPTY)
    public String getRecarregar() {
        return recarregar;
    }
    
    @JsonInclude(Include.NON_EMPTY)
    public String getRedirecionar() {
        return redirecionar;
    }
    
    @JsonInclude(Include.NON_EMPTY)
    public Map<String, Object> getRetorno() {
        return retorno;
    }

    @JsonInclude(Include.NON_EMPTY)
    public Collection<Notificacao> getNotificacoes() {
        return ImmutableList.copyOf(notificacoes);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("status", status)
            .append("mensagem", mensagem)
            .append("redirecionar", redirecionar)
            .append("recarregar", redirecionar)
            .append("retorno", retorno)
            .append("notificacoes", notificacoes)
            .toString();
    }

    /**
     * Define o status da resposta.
     */
    public static enum Status {
        SUCESSO, ERRO
    }
}
