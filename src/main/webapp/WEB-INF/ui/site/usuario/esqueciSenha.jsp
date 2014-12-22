<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:eval var="baseUrl" expression="@environment['site.url.base']" scope="request" />

<div class="cabecalho-modal">
  <button type="button" class="fechar" data-esconder="modal">&times;</button>
  <h2>Esqueci minha senha</h2>
</div>
<div class="corpo-modal esqueci-senha">
  <form id="formEsqueci" class="form form-modal" action="${baseUrl}/senha/esqueci" method="post">
    <div class="grupo-controle">
      <label class="rotulo" for="esqueciEmail">E-mail</label>
      <div class="controles">
        <input type="text" class="span5" id="esqueciEmail" name="email" data-foco="iniciado">
        <span class="msg-erro esconder" id="emailErro"></span>
      </div>
    </div>
  </form>
</div>
<div class="rodape-modal">
  <button type="button" class="botao" data-form="#formEsqueci"
    data-processando="Solicitando">Solicitar</button>
</div>