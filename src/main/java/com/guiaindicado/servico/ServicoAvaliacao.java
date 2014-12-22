package com.guiaindicado.servico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.guiaindicado.comando.empresa.AvaliarEmpresa;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.avaliacao.Avaliacao;
import com.guiaindicado.dominio.avaliacao.RepositorioAvaliacao;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.item.RepositorioEmpresa;
import com.guiaindicado.dominio.usuario.RepositorioUsuario;
import com.guiaindicado.dominio.usuario.Usuario;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.indice.IndexadorBusca;

/**
 * Processas a solicitações referente as avaliações de empresas/produtos e serviços.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Component
public class ServicoAvaliacao {

    @Autowired private RepositorioAvaliacao repositorioAvaliacao;
    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private IndexadorBusca indexadorBusca;
    
    /**
     * Avalia uma empresa. Para realizara a avaliação o usuário deve estar logado. Além
     * disso, são aplicadas regras a avaliação para previnir possíveis ataques. Avaliações
     * inválidas não são computadas, no entanto, nenhuma mensagem de erro é enviada ao
     * usuário. O site assume que uma avaliação válida é identificada. 
     * 
     * O usuário pode a qualquer momento reconsiderar e modificar a sua avaliação.
     * 
     * @param usuarioSite Usuário ativo no site
     * @param comando Dados para avaliação
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado avaliarEmpresa(UsuarioSite usuarioSite, AvaliarEmpresa comando) {
        if (!usuarioSite.isLogado()) {
            return Resultado.erro("Você precisa estar logado para avaliar.");
        }
        
        Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
        Empresa empresa = repositorioEmpresa.getPorId(comando.getEmpresa());
        
        if (empresa == null) {
            return Resultado.erro("Não foi possível registrar sua avaliação.");
        }

        Avaliacao avaliacao = Objects.firstNonNull(
            repositorioAvaliacao.getPorEmpresa(usuario, comando.getEmpresa()), Avaliacao.criar());

        if (!avaliacao.notaAceitavel(comando.getNota())) {
            return Resultado.erro("Não foi possível registrar sua avaliação.");
        }
        
        avaliacao.setEmpresa(empresa)
            .setUsuario(usuario)
            .setNota(comando.getNota())
            .setIp(comando.getIp());

        repositorioAvaliacao.salvar(avaliacao);
        indexadorBusca.indexarEmpresa(empresa);
        
        return Resultado.sucesso("Sua avaliação foi registrada.");
    }
}
