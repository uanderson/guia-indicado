package com.guiaindicado.comando.resultado;

import com.google.common.base.Preconditions;

/**
 * Classe descritora do resultado da execução de uma operação de serviço.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class Resultado {

    private boolean sucesso;
    private String mensagem;
    private Object retorno;

    /**
     * Cria um resultado.
     * 
     * @param sucesso Indica se houve sucesso
     * @param mensagem Mensagem de sucesso ou erro
     */
    private Resultado(boolean sucesso, String mensagem) {
        this(sucesso, mensagem, null);
    }

    /**
     * Cria um resultado.
     * 
     * @param sucesso Indica se houve sucesso
     * @param mensagem Mensagem de sucesso ou erro
     * @param retorno Dado para retorno
     */
    private <E> Resultado(boolean sucesso, String mensagem, E retorno) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.retorno = retorno;
    }

    /**
     * Cria um resultado de sucesso sem mensagem.
     * 
     * @return Novo resultado
     */
    public static Resultado sucesso() {
        return new Resultado(true, "");
    }

    /**
     * Cria um resultado de sucesso com mensagem.
     * 
     * @param mensagem Mensagem de sucesso
     * @return Novo resultado
     */
    public static Resultado sucesso(String mensagem) {
        return new Resultado(true, Preconditions.checkNotNull(mensagem));
    }

    /**
     * Cria um resultado de sucesso com mensagem e objeto de retorno.
     * 
     * @param mensagem Mensagem de sucesso
     * @param retorno Dado de retorno
     * @return Novo resultado
     */
    public static <E> Resultado sucesso(String mensagem, E retorno) {
        return new Resultado(true, Preconditions.checkNotNull(mensagem),
            Preconditions.checkNotNull(retorno));
    }

    /**
     * Cria um resultado de erro sem mensagem.
     * 
     * @return Novo resultado
     */
    public static Resultado erro() {
        return new Resultado(false, "");
    }

    /**
     * Cria um resultado de erro com mensagem.
     * 
     * @param mensagem Mensagem de erro
     * @return Novo resultado
     */
    public static Resultado erro(String mensagem) {
        return new Resultado(false, Preconditions.checkNotNull(mensagem));
    }

    /**
     * Cria um resultado de erro com mensagem e objeto de retorno.
     * 
     * @param mensagem Mensagem de erro
     * @param retorno Dado de retorno
     * @return Novo resultado
     */
    public static <E> Resultado erro(String mensagem, E retorno) {
        return new Resultado(false, Preconditions.checkNotNull(mensagem),
            Preconditions.checkNotNull(retorno));
    }

    /**
     * Verifica se houve sucesso na execução.
     * 
     * @return true se existir sucesso, false caso contrário
     */
    public boolean houveSucesso() {
        return sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }
    
    @SuppressWarnings("unchecked")
    public <E> E getRetorno() {
        return (E) retorno;
    }
}
