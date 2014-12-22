package com.guiaindicado.servico.email;

import java.util.Collection;
import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.guiaindicado.dominio.email.Email;

/**
 * Serviço que realiza entrega efetiva do e-mail.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class EntregadorEmail {
    
    private static final Logger LOG = LoggerFactory.getLogger(EntregadorEmail.class);

    @Value("#{environment['site.email.protocolo']}") String protocolo;
    @Value("#{environment['site.email.host']}") String host;
    @Value("#{environment['site.email.porta']}") Integer porta;
    @Value("#{environment['site.email.de']}") String de;
    @Value("#{environment['site.email.senha']}") String senha;
    @Value("#{environment['site.email.charset']}") String charset;
    @Value("#{environment['site.email.tipo_conteudo']}") String tipoConteudo;
    
    @Autowired private Session sessao;
    
    /**
     * Entrega apenas um e-mail por vez.
     * 
     * @param email E-mail a ser entregue
     * @return Registro com informação da entrega
     */
    public RegistroEntrega entregar(Email email) {
        return entregar(Lists.newArrayList(email));
    }
    
    /**
     * Entrega uma coleção de e-mails.
     * 
     * @param emails E-mail a serem entregues
     * @return Registro com informação da entrega
     */
    public RegistroEntrega entregar(Collection<Email> emails) {
        return entregarInterno(emails);
    }

    /**
     * Entrega efetivamente o e-mail, abrindo um canal de conexão com o servidor. Mantém um registro
     * de entrega mostrando quais e-mails foram ou não entregues.
     * 
     * @param emails E-mails a serem entregues
     * @return Registro com informação da entrega
     */
    private RegistroEntrega entregarInterno(Collection<Email> emails) {
        Transport transporte;
        
        try {
            transporte = sessao.getTransport(protocolo);
            transporte.connect(host, de, senha);
        } catch (MessagingException ex) {
            LOG.error("Não foi possível conectar ao servidor", ex);
            return RegistroEntrega.criarFalha(emails);
        }
        
        Collection<Email> falhaEnvio = Lists.newArrayList();
        
        try {
            for (Email email : emails) {
                try {
                    String[] para = email.getPara().split(",");
                    InternetAddress[] enderecos = new InternetAddress[para.length];
                    
                    for (int index = 0; index < para.length; index++) {
                        enderecos[index] = new InternetAddress(para[index]);
                    }
                    
                    MimeMessage mensagem = new MimeMessage(sessao);
                    mensagem.setHeader("Content-Type", tipoConteudo);
                    mensagem.setSentDate(new Date());
                    mensagem.setFrom(new InternetAddress(de));
                    mensagem.addRecipients(Message.RecipientType.TO, enderecos);
                    mensagem.setSubject(email.getAssunto(), charset);
                    mensagem.setContent(email.getConteudo(), tipoConteudo);
                    mensagem.saveChanges();
                    
                    transporte.sendMessage(mensagem, mensagem.getAllRecipients());
                } catch (MessagingException ex) {
                    falhaEnvio.add(email);
                }
            }
        } finally {
            try {
                transporte.close();
            } catch (MessagingException ex) {
                LOG.error("Não foi possível fechar a conexão com o servidor", ex);
            }
        }
        
        if (falhaEnvio.isEmpty()) {
            LOG.info("{} e-mail(s) entregue(s)", (emails.size() - falhaEnvio.size()));
            return RegistroEntrega.criarSucesso();
        } else {
            LOG.info("{} e-mail(s) não entregue(s)", emails.size());
            return RegistroEntrega.criarFalha(falhaEnvio);
        }
    }
}
