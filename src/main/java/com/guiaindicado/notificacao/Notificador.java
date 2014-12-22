package com.guiaindicado.notificacao;

import java.text.MessageFormat;
import java.util.Collection;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

/**
 * Classe que mantém a referencia de erros ocorridos durante a execução da requisição. Essa classe
 * mantém uma referencia a uma ThreadLocal, que retém tais mensagens.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class Notificador {

    private static ThreadLocal<Notificador> instancia = new ThreadLocal<Notificador>();
    private Collection<Notificacao> notificacoes = Lists.newArrayList();
    
    private Notificador() {}
    
    /**
     * Obtém a referência da thread local. Caso não exista ainda, cria.
     * 
     * @return Notificador
     */
    private static Notificador get() {
        if (instancia.get() == null) {
            instancia.set(new Notificador());
        }

        return instancia.get();
    }
    
    /**
     * Remove a referência do notificador da thread local.
     */
    public static void liberar() {
        instancia.remove();
    }
    
    /**
     * Notifica um erro.
     * 
     * @param origem Quem gerou o erro
     * @param mensagem Mensagem de erro
     */
    public static void erro(String origem, String mensagem) {
        erro(origem, mensagem, new Object[] {});
    }
    
    /**
     * Notifica um erro, formatando a mensagem com os argumentos passados. A utilização desse método
     * deve utilizar o sistema de formatação relativo a {@link MessageFormat#format(String, Object...)).
     * 
     * @param origem Quem gerou o erro
     * @param mensagem Mensagem de erro
     * @param argumentos Valores para formatar a mensagem
     */
    public static void erro(String origem, String mensagem, Object... argumentos) {
        String mensagemFormatada = MessageFormat.format(mensagem, argumentos);
        get().notificacoes.add(Notificacao.of(origem, mensagemFormatada));
    }
    
    /**
     * Verifica se existe erros notificados.
     * 
     * @return true se existir erros, false caso contrário
     */
    public static boolean temErro() {
        return !get().notificacoes.isEmpty();
    }
    
    /**
     * Verifica se existe erro especifico.
     * 
     * @param origem Quem gerou o erro
     * @return true se existir erro, false caso contrário
     */
    public static boolean temErro(final String origem) {
        Preconditions.checkNotNull(origem);
        
        Optional<Notificacao> notificacao = 
            Iterables.tryFind(get().notificacoes, new Predicate<Notificacao>() {
                @Override public boolean apply(Notificacao item) {
                    return item.getOrigem().equals(origem);
                }
            });
        
        return notificacao.isPresent();
    }

    /**
     * Obtém uma cópia das notificações. A cópia é necessária para não haver adições fora do
     * contexto do Notificador.
     * 
     * @return true
     */
    public static Collection<Notificacao> getNotificacoes() {
        return ImmutableList.copyOf(get().notificacoes);
    }
}
