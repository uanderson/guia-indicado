<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Banner" subtitulo="Cadastro">
  <form id="form" action="${baseUrl}/banners/cadastro" method="post">
    <input type="hidden" name="id" value="${banner.id}" />
    <div class="row-fluid">
      <div class="span12">
        <label for="anunciante">Anunciante</label>
        <input type="text" id="anunciante" class="span12" value="${banner.nomeAnunciante}" data-foco="iniciado">
        <input type="hidden" id="codigoAnunciante" name="anunciante" value="${banner.anunciante}">
        <span class="msg-erro esconder" id="anuncianteErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span12">
        <label for="url">URL</label>
        <input type="text" id="url" name="url" class="span12" value="${banner.url}" />
        <span class="msg-erro esconder" id="urlErro"></span>
      </div>
    </div>
    <div class="row-fluid">
      <div class="span4">
        <label for="posicao">Posição</label>
        <select id="posicao" name="posicao" class="span12">
          <option value="">Selecione</option>
          <c:forEach var="item" items="${posicoes}">
            <option value="${item}" <c:if test="${banner.posicao == item}">selected="selected"</c:if>>${item.descricao}</option>
          </c:forEach>
        </select>
        <span class="msg-erro esconder" id="posicaoErro"></span>
      </div>
      <div class="span8">
        <label>Banner</label>
        <div class="input-append">
          <input type="file" id="bannerTemp" name="bannerTemp" style="display: none" 
            data-url="${baseUrl}/banners/temp" />
          <input id="banner" name="banner" class="span9" type="text" readonly="readonly">
          <button type="button" id="selecao" class="btn">Selecionar</button>
        </div>
        <span class="msg-erro esconder" id="bannerErro"></span>
      </div>
    </div>
    <div id="progresso" class="row-fluid margin" style="display: none;">
      <div class="progress progress-striped">
        <div class="bar" style="width: 0%;"></div>
      </div>
    </div>
    <div class="form-actions">
      <button id="confirmar" type="button" class="btn btn-primary" data-form="#form"
        data-loading-text="Confirmando">Confirmar</button>
    </div>
  </form>
</gt:containerIntitulado>
