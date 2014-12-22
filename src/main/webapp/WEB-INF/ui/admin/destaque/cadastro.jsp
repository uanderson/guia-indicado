<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Destaque" subtitulo="Cadastro">
  <form id="formDestaque" action="${baseUrl}/destaques/cadastro" method="post">
    <input type="hidden" name="id" value="${destaque.id}" />
    <div class="row-fluid">
      <div class="span12">
        <label for="empresa">Empresa</label>
        <input type="text" id="empresa" class="span12" value="${destaque.nomeEmpresa}" data-foco="iniciado">
        <input type="hidden" id="codigoEmpresa" name="empresa" value="${destaque.empresa}">
        <span class="msg-erro esconder" id="empresaErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="titulo">Título</label>
        <input type="text" id="titulo" name="titulo" class="span12" value="${destaque.titulo}" maxlength="18" />
        <span class="msg-erro esconder" id="tituloErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="texto">Texto</label>
        <input type="text" id="texto" name="texto" class="span12" value="${destaque.texto}" maxlength="26" />
        <span class="msg-erro esconder" id="textoErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span3">
        <label>Destaque vertical</label>
        <label class="radio inline">
          <input type="radio" name="vertical" value="true" 
            <c:if test="${destaque.vertical}">checked="checked"</c:if>> Sim
        </label>
        <label class="radio inline">
          <input type="radio" name="vertical" value="false" 
            <c:if test="${!destaque.vertical}">checked="checked"</c:if>> Não
        </label>
        <span class="msg-erro esconder" id="verticalErro"></span>
      </div>
      <div class="span9">
        <label>Imagem</label>
        <div class="input-append">
          <input type="file" id="imagemTemp" name="imagemTemp" style="display: none" 
            data-url="${baseUrl}/destaques/temp" />
          <input id="imagem" name="imagem" class="span9" type="text" readonly="readonly">
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
      <button id="confirmar" type="button" class="btn btn-primary" data-form="#formDestaque"
        data-loading-text="Confirmando">Confirmar</button>
    </div>
  </form>
</gt:containerIntitulado>
