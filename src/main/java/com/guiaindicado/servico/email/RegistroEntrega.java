package com.guiaindicado.servico.email;

import java.util.Collection;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.guiaindicado.dominio.email.Email;

/**
 * Retém informações sobre a entrega de e-mails pelo entregador.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class RegistroEntrega {

    private Collection<Email> falhaEnvio = Lists.newArrayList();

    /**
     * Constrói um registro de entrega com as falhas ocorridas.
     * 
     * @param falhaEnvio Coleção de e-mails que falharam.
     */
    private RegistroEntrega(Collection<Email> falhaEnvio) {
        this.falhaEnvio.addAll(Preconditions.checkNotNull(falhaEnvio));
    }

    /**
     * Cria um registro de entrega para sucesso. Nesse caso, o envio de nenhum e-mail
     * falhou.
     * 
     * @return Novo registro de entrega
     */
    public static RegistroEntrega criarSucesso() {
        return new RegistroEntrega();
    }
    
    /**
     * Cria um registro de entrega para erro. Nesse caso, o envio de algum e-mail falhou.
     * 
     * @param falhaEnvio
     * @return Novo registro de entrega
     */
    public static RegistroEntrega criarFalha(Collection<Email> falhaEnvio) {
        return new RegistroEntrega(falhaEnvio);
    }

    /**
     * Indica se houve sucesso na entrega dos e-mails.
     *     
     * @return Sucesso se todos foram entregues, falha caso contrário
     */
    public boolean houveSucesso() {
        return falhaEnvio.isEmpty();
    }

    /**
     * Obtém a lista de falhas de entrega.
     * 
     * @return Coleção de falhas de entrega
     */
    public Collection<Email> getFalhaEnvio() {
        return ImmutableList.copyOf(falhaEnvio);
    }
    
    private RegistroEntrega() {
    }
}
