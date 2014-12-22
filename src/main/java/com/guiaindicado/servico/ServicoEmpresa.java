package com.guiaindicado.servico;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.guiaindicado.comando.empresa.IndicarEmpresa;
import com.guiaindicado.comando.empresa.SalvarEmpresa;
import com.guiaindicado.comando.resultado.Resultado;
import com.guiaindicado.dominio.item.Categoria;
import com.guiaindicado.dominio.item.Empresa;
import com.guiaindicado.dominio.item.RepositorioCategoria;
import com.guiaindicado.dominio.item.RepositorioEmpresa;
import com.guiaindicado.dominio.localizacao.Cidade;
import com.guiaindicado.dominio.localizacao.RepositorioCidade;
import com.guiaindicado.dominio.usuario.RepositorioUsuario;
import com.guiaindicado.dominio.usuario.Usuario;
import com.guiaindicado.seguranca.UsuarioSite;
import com.guiaindicado.servico.indice.IndexadorBusca;
import com.guiaindicado.validacao.suporte.ValidadorBean;

/**
 * Processa as operações referentes a empresa.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Service
public class ServicoEmpresa {

    @Autowired private RepositorioEmpresa repositorioEmpresa;
    @Autowired private RepositorioCategoria repositorioCategoria;
    @Autowired private RepositorioCidade repositorioCidade;
    @Autowired private RepositorioUsuario repositorioUsuario;
    @Autowired private IndexadorBusca indexadorBusca;

    /**
     * Realiza a indicação da empresa para o Guia Indicado. A empresa quando indicada entra no
     * estado de moderação.
     * 
     * @param comando Dados da empresa
     * @return Sucesso ou falha da operação
     */
    @Transactional
    public Resultado indicarEmpresa(UsuarioSite usuarioSite, IndicarEmpresa comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro("Verifique os erros na indicação");
        }
        
        Categoria categoria = repositorioCategoria.getPorId(comando.getCategoria());
        Cidade cidade = repositorioCidade.getPorId(comando.getCidade());
        Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
        
        Empresa empresa = Empresa.criar(usuario)
            .setNome(comando.getNome())
            .setReferencia(comando.getNome())
            .setDescricao(comando.getDescricao())
            .setCategoria(categoria)
            .setTags(comando.getTags())
            .setCep(comando.getCep())
            .setEndereco(comando.getEndereco())
            .setNumero(comando.getNumero())
            .setBairro(comando.getBairro())
            .setCidade(cidade)
            .setTelefone(comando.getTelefone())
            .setCelular(comando.getCelular())
            .setEmail(comando.getEmail())
            .setSite(comando.getSite())
            .setLatitude(0.0)
            .setLongitude(0.0);
        
        repositorioEmpresa.salvar(empresa);
        
        return Resultado.sucesso(
            "Obrigado pela sua indicação. A empresa está em processo de moderação.");
    }
    
    /**
     * Salva a empresa. Essa ação, marca a empresa para revisão sempre.
     * 
     * @param comando Dados da empresa
     * @return Sucesso ou falha da ação
     */
    @Transactional
    public Resultado salvarEmpresa(UsuarioSite usuarioSite, SalvarEmpresa comando) {
        if (!ValidadorBean.estaValido(comando)) {
            return Resultado.erro("Verifique os erros do cadastro");
        }
        
        Categoria categoria = repositorioCategoria.getPorId(comando.getCategoria());
        Cidade cidade = repositorioCidade.getPorId(comando.getCidade());
        Usuario usuario = repositorioUsuario.getPorEmail(usuarioSite.getUsername());
        
        Empresa empresa = Objects.firstNonNull(
            repositorioEmpresa.getPorId(comando.getId()), Empresa.criar(usuario));
      
        empresa.setDescricao(comando.getDescricao())
            .setNome(comando.getNome())
            .setReferencia(comando.getNome())
            .setCategoria(categoria)
            .setTags(comando.getTags())
            .setCep(comando.getCep())
            .setEndereco(comando.getEndereco())
            .setNumero(comando.getNumero())
            .setBairro(comando.getBairro())
            .setCidade(cidade)
            .setTelefone(comando.getTelefone())
            .setCelular(comando.getCelular())
            .setEmail(comando.getEmail())
            .setSite(comando.getSite())
            .setLatitude(comando.getLatitude())
            .setLongitude(comando.getLongitude());
        
        empresa.revisar();
        repositorioEmpresa.salvar(empresa);
        
        return Resultado.sucesso("Empresa salva com sucesso.");
    }
    
    /**
     * Realiza a moderação da empresa. Após o sucesso, envia um e-mail ao usuário dono da empresa
     * dizendo que a empresa foi moderada.
     * 
     * @param id Id da empresa
     * @return Sucesso ou falha da ação
     */
    @Transactional
    public Resultado moderarEmpresa(int id) {
        Empresa empresa = repositorioEmpresa.getPorId(id);
        
        if (empresa == null) {
            return Resultado.erro("Não foi possível moderar a empresa.");
        }

        empresa.moderar();
        repositorioEmpresa.salvar(empresa);
        indexadorBusca.indexarEmpresa(empresa);

        return Resultado.sucesso("Empresa moderada com sucesso.");
    }
}
