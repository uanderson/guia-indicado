package com.guiaindicado.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.comando.usuario.AlterarSenha;
import com.guiaindicado.comando.usuario.AlterarUsuario;
import com.guiaindicado.comando.usuario.ReiniciarSenha;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.dominio.localizacao.RepositorioCidade;
import com.guiaindicado.dominio.usuario.RepositorioTokenUsuario;
import com.guiaindicado.dominio.usuario.RepositorioUsuario;
import com.guiaindicado.dominio.usuario.Usuario;
import com.guiaindicado.notificacao.Notificador;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações do usuário. Como alteração de senha e dados.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class ServicoUsuario {

    @Autowired private RepositorioCidade repositorioCidade;
    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioTokenUsuario repositorioTokenUsuario;

    /**
     * Salva os dados do usuário. O e-mail nesse caso não é alterado, então não é
     * necessário verificar a existência na base, mesmo o usuário alterando direto no HTML
     * o valor.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados para alteração
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado alterarUsuario(UsuarioSite usuarioSite, AlterarUsuario comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro();
        }
        
        Cidade cidade = repositorioCidade.getPorId(comando.getCidade());
        Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
        usuario.setNome(comando.getNome())
            .setCidade(cidade)
            .setReceberEmail(comando.getReceberEmail());
        
        repositorioUsuario.salvar(usuario);
        
        return Resultado.sucesso("Seus dados foram alterados.");
    }
    
    /**
     * Realiza a alteração de senha do usuário. O usuário deve informar a sua senha atual para
     * realizar a operação.
     * 
     * @param usuarioSite Usuário ativo
     * @param comando Dados para alteração
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado alterarSenha(UsuarioSite usuarioSite, AlterarSenha comando) {
        if (!ValidadorBean.estaValido(comando)
                || !compararSenha(comando.getSenha(), comando.getConfirmacao())) {
            return Resultado.erro();
        }
        
        Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
        
        if (!usuario.compararSenha(comando.getSenhaAtual())) {
            Notificador.erro("senhaAtual", "Senha incorreta");
            return Resultado.erro();
        }
        
        usuario.definirSenha(comando.getSenha());
        repositorioUsuario.salvar(usuario);
        
        return Resultado.sucesso("Sua senha foi alterada.");
    }
    
    /**
     * Efetivamente altera a senha do usuário. Para realizar a gravação, as senhas são verificadas,
     * assim como o comando. Essa operação não pode ser executada se não fizer parte de outra
     * transação.
     * 
     * @param usuario Usuário que terá a senha reiniciada
     * @param comando Dados para reinicio
     * @return
     */
    @Transactional(propagation = Propagation.MANDATORY)
    public Resultado reiniciarSenha(Usuario usuario, ReiniciarSenha comando) {
        if (!ValidadorBean.estaValido(comando) || 
                !compararSenha(comando.getSenha(), comando.getConfirmacao())) {
            return Resultado.erro();
        }
        
        usuario.definirSenha(comando.getSenha());
        repositorioUsuario.salvar(usuario);
        
        return Resultado.sucesso();
    }

    /**
     * Verifica se as senhas são iguais.
     * 
     * @param senha Senha original
     * @param confirmacao Senha para confirmação
     * @return true se forem iguais, false caso contrário
     */
    private boolean compararSenha(String senha, String confirmacao) {
        if (!Notificador.temErro("senha") && !Notificador.temErro("confirmacao")
                && (!confirmacao.equals(senha))) {
            Notificador.erro("confirmacao", "As senhas não conferem");
        }
        
        return !Notificador.temErro();
    }
}
