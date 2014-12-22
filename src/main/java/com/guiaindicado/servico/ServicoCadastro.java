package com.guiaindicado.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.CadastrarUsuario;
import com.guiaindicado.comando.usuario.ConfirmarCadastro;
import com.guiaindicado.dominio.email.ModeloEmail;
import com.guiaindicado.dominio.localizacao.RepositorioCidade;
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
 * Processa as operações do cadastro do usuário.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class ServicoCadastro {

    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioTokenUsuario repositorioTokenUsuario;
    @Autowired private RepositorioCidade repositorioCidade;
    @Autowired private EnviadorEmail enviadorEmail;
    @Autowired private RenderizadorEmail renderizadorEmail;
    
    /**
     * Realiza o cadastramento do usuário no site e gera um token que será enviado ao usuário para
     * confirmação do seu cadastro.
     * 
     * @param comando Dados do usuário
     * @return Sucesso ou falha da operação.
     */
    @Transactional
    public Resultado cadastrarUsuario(CadastrarUsuario comando) {
        if (!comandoValido(comando)) {
            return Resultado.erro();
        }
        
        Usuario usuario = Usuario.criar(comando.getEmail())
            .setNome(comando.getNome())
            .setReceberEmail(comando.getReceberEmail())
            .setCidade(repositorioCidade.getPorId(comando.getCidade()));
        
        usuario.definirSenha(comando.getSenha());
        
        TokenUsuario token = TokenUsuario.criar(usuario, TokenUsuario.Tipo.CONFIRMACAO_CADASTRO);
        repositorioUsuario.salvar(usuario);
        repositorioTokenUsuario.salvar(token);
        enviarConfirmacao(usuario, token);
        
        return Resultado.sucesso(
            "Confirme seu cadastro através do e-mail que enviamos para você.");
    }

    /**
     * Faz a validação do token e confirma o cadastro do usuário através do mesmo. Após a
     * confirmação, remove o token da base de dados e envia e-mail de boas vindas.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados de confirmação
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado confirmarCadastro(UsuarioSite usuarioSite, ConfirmarCadastro comando) {
        TokenUsuario tokenUsuario = repositorioTokenUsuario.getPorToken(comando.getToken());
        boolean tokenValido = (tokenUsuario != null && tokenUsuario.valido());
        
        if (usuarioSite.isLogado() && !tokenValido) {
            Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
            return Resultado.erro("Seu cadastro (" + usuario.getEmail() + ") já foi confirmado.");
        }

        if (!tokenValido) {
            return Resultado.erro("O link que você está acessando não tem mais validade.");
        }
        
        Usuario usuario = tokenUsuario.getUsuario();
        usuario.habilitar();
        
        repositorioUsuario.salvar(usuario);
        repositorioTokenUsuario.remover(tokenUsuario);
        enviarBoasVindas(usuario);
        
        return Resultado.sucesso(
            "Seu cadastro foi confirmado " + usuario.getNome() + ". Acesse o Guia Indicado.");
    }
    
    /**
     * Envia um e-mail de confirmação ao usuário.
     * 
     * @param usuario Usuário que receberá o e-mail
     * @param tokenUsuario Token gerado para a confirmação
     */
    private void enviarConfirmacao(Usuario usuario, TokenUsuario tokenUsuario) {
        ModeloEmail modelo = ModeloEmail.criar(ModeloEmail.Tipo.CONFIRMACAO_CADASTRO)
            .addPropriedade("nome", usuario.getNome())
            .addPropriedade("token", tokenUsuario.getToken());
        
        enviadorEmail.enviarAssincrono(modelo.gerarEmail(usuario.getEmail(), renderizadorEmail));
    }
    
    /**
     * Envia e-mail de boas vindas ao usuário.
     * 
     * @param usuario Usuário que receberá o e-mail
     */
    private void enviarBoasVindas(Usuario usuario) {
        ModeloEmail modelo = ModeloEmail.criar(ModeloEmail.Tipo.BEM_VINDO)
            .addPropriedade("nome", usuario.getNome());
        
        enviadorEmail.enviarAssincrono(modelo.gerarEmail(usuario.getEmail(), renderizadorEmail));
    }
    
    /**
     * Verifica se o comando passado está válido.
     * 
     * @param comando Dados para a operação
     * @return true se for válido, false caso contrário
     */
    private boolean comandoValido(CadastrarUsuario comando) {
        ValidadorBean.estaValido(comando);

        if (!Notificador.temErro("email")
            && repositorioUsuario.usuarioCadastrado(comando.getEmail())) {
            Notificador.erro("email", "Esse e-mail já está sendo utilizado");
        }

        return !Notificador.temErro();
    }
}
