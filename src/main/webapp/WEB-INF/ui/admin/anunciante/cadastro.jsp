<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>
<%@ taglib prefix="gn" uri="http://guiaindicado.com/jsp/jstl/funcoes" %>

<gt:containerIntitulado titulo="Anunciante" subtitulo="Cadastro">
  <form id="formCadastroAnunciante" action="${baseUrl}/anunciantes/cadastro" method="post">
    <input type="hidden" name="id" value="${anunciante.id}" />
    <div class="row-fluid">
      <div class="span12">
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" value="${anunciante.nome}" class="span12" data-foco="iniciado" />
        <span class="msg-erro" id="nomeErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="nomeCidade">Cidade</label>
        <input type="text" id="cidade" class="span12"
          value="${anunciante.nomeCidade}${empty anunciante.siglaEstado ? '' : ' ('.concat(anunciante.siglaEstado).concat(')')}"  />
        <input type="hidden" id="codigoCidade" name="cidade" value="${anunciante.cidade}" />
        <span class="msg-erro" id="cidadeErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span4">
        <label for="telefone">Telefone</label>
        <input type="text" id="telefone" name="telefone" data-mask="phone"
          value="${gn:formatarTelefone(anunciante.telefone)}" class="span12" />
        <span class="msg-erro" id="telefoneErro"></span>
      </div>
      <div class="span8">
        <label for="email">Email</label>
        <input type="text" id="email" name="email" value="${anunciante.email}" class="span12" />
        <span class="msg-erro" id="emailErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="site">Site</label>
        <input type="text" id="site" name="site" value="${anunciante.site}" class="span12" />
        <span class="msg-erro" id="siteErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label>Imagem</label>
        <div class="input-append">
          <input type="file" id="imagemTemp" name="imagemTemp" style="display: none" 
            data-url="${baseUrl}/anunciantes/temp" />
          <input id="imagem" name="imagem" class="span10" type="text" readonly="readonly">
          <button type="button" id="selecao" class="btn">Selecionar</button>
        </div>
        <span class="msg-erro esconder" id="imagemErro"></span>
      </div>
    </div>
    <div id="progresso" class="row-fluid margin" style="display: none;">
      <div class="progress progress-striped">
        <div class="bar" style="width: 0%;"></div>
      </div>
    </div>
    <div class="form-actions">
      <button id="confirmar" type="button" class="btn btn-primary" data-form="#formCadastroAnunciante"
        data-loading-text="Confirmando">Confirmar</button>
    </div>
  </form>
</gt:containerIntitulado>
