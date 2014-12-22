package com.guiaindicado.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.ReiniciarSenha;
import com.guiaindicado.comando.usuario.SolicitarReinicioSenha;
import com.guiaindicado.dominio.email.ModeloEmail;
import com.guiaindicado.dominio.usuario.RepositorioTokenUsuario;
import com.guiaindicado.dominio.usuario.RepositorioUsuario;
import com.guiaindicado.dominio.usuario.TokenUsuario;
import com.guiaindicado.dominio.usuario.Usuario;
import com.guiaindicado.notificacao.Notificador;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.email.EnviadorEmail;
import com.guiaindicado.servico.email.RenderizadorEmail;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações pertinentes ao reinicio de senha. 
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class ServicoEsqueciSenha {

    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioTokenUsuario repositorioTokenUsuario;
    @Autowired private EnviadorEmail enviadorEmail;
    @Autowired private RenderizadorEmail renderizadorEmail;
    @Autowired private ServicoUsuario servicoUsuario;
    
    /**
     * Solicita o reinicio de senha. A operação só pode ocorrer se o usuário existir na base. Após a
     * confirmação, um e-mail é disparado com um token gerado para a operação de reinicio.
     * 
     * @param comando Dados para solicitação
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado solicitarReinicioSenha(SolicitarReinicioSenha comando) {
        if (!comandoValido(comando)) {
            return Resultado.erro();
        }
        
        Usuario usuario = repositorioUsuario.getPorEmail(comando.getEmail());
        
        if (!usuario.isHabilitado()) {
            return Resultado.erro(
                "Confirme seu cadastro antes de solicitar o reinício de senha.");
        }
        
        TokenUsuario tokenUsuario = TokenUsuario.criar(usuario, TokenUsuario.Tipo.ESQUECI_SENHA);
        repositorioTokenUsuario.salvar(tokenUsuario);
        enviarEmailSolicitacao(usuario, tokenUsuario);
        
        return Resultado.sucesso(
            "Enviamos um e-mail para você com intruções para o reinício da sua senha.");
    }

    /**
     * Envia e-mail ao usuário com token para reinicio de senha.
     * 
     * @param usuario Usuário que receberá o e-mail
     * @param tokenUsuario
     */
    private void enviarEmailSolicitacao(Usuario usuario, TokenUsuario tokenUsuario) {
        ModeloEmail modelo = ModeloEmail.criar(ModeloEmail.Tipo.SOLICITACAO_REINICIO_SENHA)
            .addPropriedade("nome", usuario.getNome())
            .addPropriedade("token", tokenUsuario.getToken());
        
        enviadorEmail.enviarAssincrono(modelo.gerarEmail(usuario.getEmail(), renderizadorEmail));
    }
    
    /**
     * Realiza a verificação do token de recuperação de senha. O token só é verificado se o usuário
     * não estiver logado.
     * 
     * @param usuarioSite Usuário ativo
     * @param token Token para verificação
     * @return
     */
    @Transactional
    public Resultado verificarToken(UsuarioSite usuarioSite, String token) {
        if (usuarioSite.isLogado()) {
            return Resultado.erro(
                "Você já está logado. Tente novamente após sair do Guia Indicado.");
        }

        TokenUsuario tokenUsuario = repositorioTokenUsuario.getPorToken(token);
        
        if (tokenUsuario == null || !tokenUsuario.valido()) {
            return Resultado.erro("O link que você está acessando não tem mais validade. " +
                "Solicite novamente o reinício da sua senha.");
        }

        return Resultado.sucesso();
    }
    
    /**
     * Realiza a alteração da senha do usuário. A alteração da senha só pode acontecer se o token
     * for válido.
     * 
     * @param comando Dados para reinicio
     * @return Sucesso com o e-mail do usuário, falha caso contrário
     */
    @Transactional
    public Resultado reiniciarSenha(ReiniciarSenha comando) {
        TokenUsuario tokenUsuario = repositorioTokenUsuario.getPorToken(comando.getToken());
        
        if ((tokenUsuario == null || !tokenUsuario.valido())) {
            return Resultado.erro("Não foi possível realizar a alteração de senha. " +
                "Solicite novamente o reinício.");
        }
        
        Usuario usuario = tokenUsuario.getUsuario();
        Resultado resultado = servicoUsuario.reiniciarSenha(usuario, comando);
        
        if (resultado.houveSucesso()) {
            repositorioTokenUsuario.remover(tokenUsuario);
            return Resultado.sucesso("Sua senha foi alterada.", usuario.getEmail());
        }
        
        return resultado;
    }
    
    /**
     * Verifica se o comando de solicitação de reset é válido.
     * 
     * @param comando Dados do usuário
     * @return true se for válido, false caso contrário
     */
    private boolean comandoValido(SolicitarReinicioSenha comando) {
        ValidadorBean.estaValido(Preconditions.checkNotNull(comando));

        if (!Notificador.temErro("email")
            && !repositorioUsuario.usuarioCadastrado(comando.getEmail())) {
            Notificador.erro("email", "Não existe cadastro para esse endereço de e-mail");
        }

        return !Notificador.temErro();
    }
}
