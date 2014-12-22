package com.guiaindicado.dominio.email;

import java.util.Map;

import com.google.common.collect.Maps;
import com.guiaindicado.servico.email.RenderizadorEmail;

public class ModeloEmail {

    private Tipo tipo = Tipo.INDEFINIDO;
    private Map<String, Object> contexto = Maps.newHashMap();
    
    private ModeloEmail(Tipo tipo) {
        this.tipo = tipo;
    }
    
    public static ModeloEmail criar(Tipo tipo) {
        return new ModeloEmail(tipo);
    }
    
    public ModeloEmail addPropriedade(String nome, Object valor) {
        contexto.put(nome, valor);
        return this;
    }
    
    public Email gerarEmail(String para, RenderizadorEmail renderizadorEmail) {
        return Email.criar()
            .setPara(para)
            .setAssunto(tipo.getAssunto())
            .setConteudo(renderizadorEmail.renderizar(contexto, tipo));
    }
    
    public static enum Tipo {
        
        INDEFINIDO() {
            @Override
            public String getNome() {
                throw new IllegalStateException("Indisponível para esse tipo");
            }

            @Override
            public String getAssunto() {
                throw new IllegalStateException("Indisponível para esse tipo");
            }
        },
        CONFIRMACAO_CADASTRO("/emails/confirmacaoCadastro.vm", "Confirme seu cadastro no Guia Indicado"), 
        SOLICITACAO_REINICIO_SENHA("/emails/solicitacaoReinicioSenha.vm", "Reinicie sua senha do Guia Indicado"),
        BEM_VINDO("/emails/bemVindo.vm", "Bem vindo ao Guia Indicado"),
        CONTATO("/emails/contato.vm", "Contato enviado ao Guia Indicado");

        private String nome;
        private String assunto;

        Tipo() {}
        
        Tipo(String nome, String assunto) {
            this.nome = nome;
            this.assunto = assunto;
        }

        public String getNome() {
            return nome;
        }
        
        public String getAssunto() {
            return assunto;
        }
    }
}
