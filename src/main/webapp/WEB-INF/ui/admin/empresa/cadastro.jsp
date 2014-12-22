<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>
<%@ taglib prefix="gn" uri="http://guiaindicado.com/jsp/jstl/funcoes" %>

<gt:containerIntitulado titulo="Empresa" subtitulo="Cadastro">
  <form id="form" action="${baseUrl}/empresas/cadastro" method="post">
    <input type="hidden" name="id" value="${empresa.id}" />
    <div class="row-fluid">
      <div class="span12">
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${empresa.nome}" class="span12" data-foco="iniciado" />
        <span class="msg-erro" id="nomeErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="descricao">Descrição</label>
        <textarea id="descricao" name="descricao" rows="3" class="span12">${empresa.descricao}</textarea>
        <span class="msg-erro" id="sobreErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="categoria">Categoria</label>
        <input type="text" id="categoria" name="nomeCategoria" class="span12" value="${empresa.nomeCategoria}" />
        <input type="hidden" id="codigoCategoria" name="categoria" value="${empresa.categoria}" />
        <span class="msg-erro esconder" id="categoriaErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="sobre">Palavras-chave</label>
        <textarea id="tags" name="tags" rows="2" class="span12">${empresa.tags}</textarea>
        <span class="msg-erro" id="tagsErro"></span>
      </div>
    </div>
    <hr />
    <ul class="nav nav-tabs">
      <li class="active"><a href="#abaEndereco" data-toggle="tab">Endereço</a></li>
      <li><a href="#abaContato" data-toggle="tab">Contato</a></li>
      <li><a href="#abaLocalizacao" data-toggle="tab">Localização</a></li>
    </ul>
    <div class="tab-content">
      <div class="tab-pane fade in active noverflow" id="abaEndereco">
        <div class="row-fluid">
          <div class="span12">
            <label for="cep">CEP</label>
            <input type="text" id="cep" name="cep" data-mask="cep" 
              value="${gn:formatarCep(empresa.cep)}" class="span3" />
              <span class="msg-erro" id="cepErro"></span>
          </div>
        </div>
        <div class="row-fluid">
          <div class="span9">
            <label for="endereco">Endereço</label>
            <input type="text" id="endereco" name="endereco" value="${empresa.endereco}" 
              class="span12" />
            <span class="msg-erro" id="enderecoErro"></span>
          </div>
          <div class="span3">
            <label for="numero">Número</label>
            <input type="text" id="numero" name="numero" value="${empresa.numero}" class="span12" />
            <span class="msg-erro" id="numeroErro"></span>
          </div>
        </div>
        <div class="row-fluid">
          <div class="span12">
            <label for="bairro">Bairro</label>
            <input type="text" id="bairro" name="bairro" value="${empresa.bairro}" class="span12" />
            <span class="msg-erro" id="bairroErro"></span>
          </div>
        </div>
        <div class="row-fluid">
          <div class="span12">
            <label for="nomeCidade">Cidade</label>
            <input type="text" id="cidade" class="span12"
              value="${gn:formatarCidade(empresa.nomeCidade, empresa.siglaEstado)}"  />
            <input type="hidden" id="codigoCidade" name="cidade" value="${empresa.cidade}" />
            <span class="msg-erro" id="cidadeErro"></span>
          </div>
        </div>
      </div>
      <div class="tab-pane fade in" id="abaContato">
        <div class="row-fluid">
          <div class="span4">
            <label for="telefone">Telefone</label>
            <input type="text" id="telefone" name="telefone" data-mask="phone"
              value="${gn:formatarTelefone(empresa.telefone)}" class="span12" />
            <span class="msg-erro" id="telefoneErro"></span>
          </div>
          <div class="span4">
            <label for="celular">Celular</label>
            <input type="text" id="celular" name="celular" data-mask="phone"
              value="${gn:formatarTelefone(empresa.celular)}" class="span12" />
            <span class="msg-erro" id="celularErro"></span>
          </div>
        </div>
        <div class="row-fluid">
          <div class="span12">
            <label for="email">Email</label>
            <input type="text" id="email" name="email" value="${empresa.email}" class="span12" />
            <span class="msg-erro" id="emailErro"></span>
          </div>
        </div>
        <div class="row-fluid">
          <div class="span12">
            <label for="site">Site</label>
            <input type="text" id="site" name="site" value="${empresa.site}" class="span12" />
            <span class="msg-erro" id="siteErro"></span>
          </div>
        </div>
      </div>
      <div class="tab-pane fade in" id="abaLocalizacao">
        <div class="row-fluid">
          <div class="span4">
            <label for="latitude">Latitude</label>
            <input type="text" id="latitude" name="latitude" data-mask="latitude"
              value="<s:eval expression="empresa.latitude"/>" class="span12" />
            <span class="msg-erro" id="latitudeErro"></span>
          </div>
          <div class="span4">
            <label for="longitude">Longitude</label>
            <input type="text" id="longitude" name="longitude" data-mask="longitude"
              value="<s:eval expression="empresa.longitude"/>" class="span12" />
            <span class="msg-erro" id="longitudeErro"></span>
          </div>
        </div>
      </div>
    </div>
    <div class="form-actions">
      <button id="confirmar" type="button" class="btn btn-primary" data-form="#form"
        data-loading-text="Confirmando">Confirmar</button>
    </div>
  </form>
</gt:containerIntitulado>
