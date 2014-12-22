package com.guiaindicado.seguranca.autenticacao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.google.common.collect.Iterables;
import com.guiaindicado.seguranca.PermissaoSite;
import com.guiaindicado.seguranca.UsuarioLogado;

@Component
public class ServicoAutenticacaoUsuario implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(ServicoAutenticacaoUsuario.class);

    private static final String SQL_USUARIO = 
        "SELECT usuario_id AS id, nome, email AS username, senha, habilitado " +
        " FROM usuario WHERE email = ?";

    private static final String SQL_PERMISSAO = 
        "SELECT nome FROM permissao AS p INNER JOIN usuario_x_permissao AS uxp " +
        " ON p.permissao_id = uxp.permissao_id WHERE usuario_id = ?";

    private JdbcTemplate executorSql;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UsuarioLogado usuario = getUsuarioLogado(username);
        usuario.addPermissoes(getPermissoesSite(usuario));
        
        return usuario;
    }

    private UsuarioLogado getUsuarioLogado(String username) {
        Collection<UsuarioLogado> usuarios = executorSql.query(SQL_USUARIO, 
            new MapeadorUsuarioLogado(), username);

        if (usuarios.isEmpty()) {
            LOG.info("Usuário não encontrado {}", username);
            throw new UsernameNotFoundException("Senha ou e-mail inválido");
        } else {
            return Iterables.get(usuarios, 0);
        }
    }
    
    private Collection<PermissaoSite> getPermissoesSite(UsuarioLogado usuario) {
        Collection<PermissaoSite> permissoes = executorSql.query(SQL_PERMISSAO,
            new MapeadorPermissaoSite(), usuario.getId());
        
        if (permissoes.isEmpty()) {
            LOG.error("Nenhuma permissão encontrada para o usuário {}", usuario.getUsername());
            throw new UsernameNotFoundException("Usuário sem permissão de acesso");
        }
        
        return permissoes;
    }

    @Autowired
    protected void setDataSource(DataSource dataSource) {
        executorSql = new JdbcTemplate(dataSource);
    }

    private static class MapeadorUsuarioLogado implements ParameterizedRowMapper<UsuarioLogado> {

        @Override
        public UsuarioLogado mapRow(ResultSet rs, int row) throws SQLException {
            UsuarioLogado detalhe = UsuarioLogado.construtor()
                .comId(rs.getInt("id"))
                .comNome(rs.getString("nome"))
                .comUsername(rs.getString("username"))
                .comSenha(rs.getString("senha"))
                .comHabilitado(rs.getBoolean("habilitado"))
                .construir();

            return detalhe;
        }
    }

    private static class MapeadorPermissaoSite implements ParameterizedRowMapper<PermissaoSite> {

        @Override
        public PermissaoSite mapRow(ResultSet rs, int row) throws SQLException {
            return PermissaoSite.criar(rs.getString("nome"));
        }
    }
}
