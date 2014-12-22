<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:eval var="baseUrl" expression="@environment['site.url.base']" scope="request" />

<div class="cabecalho-modal">
  <button type="button" class="fechar" data-esconder="modal">&times;</button>
  <h2>Contato</h2>
</div>
<div class="corpo-modal contato">
  <form id="formPopup" class="form form-modal" action="${baseUrl}/contatar" method="post">
    <div class="grupo-controle">
      <label class="rotulo" for="contatoNome">Nome</label>
      <div class="controles">
        <input type="text" id="contatoNome" name="nome" class="span5" data-foco="iniciado" />
        <span class="msg-erro esconder" id="nomeErro"></span>
      </div>
    </div>
    <div class="grupo-controle">
      <label class="rotulo" for="contatoEmail">E-mail</label>
      <div class="controles">
        <input type="text" id="contatoEmail" name="email" class="span5" />
        <span class="msg-erro esconder" id="emailErro"></span>
      </div>
    </div>
    <div class="grupo-controle">
      <label class="rotulo" for="contatoTelefone">Telefone</label>
      <div class="controles">
        <input type="text" id="contatoTelefone" name="telefone" class="span2" data-mask="phone" />
        <span class="msg-erro esconder" id="telefoneErro"></span>
      </div>
    </div>
    <div class="grupo-controle">
      <label class="rotulo" for="contatoMensagem">Mensagem</label>
      <div class="controles">
        <textarea rows="6" id="contatoMensagem" name="mensagem" class="span5"></textarea>
        <span class="msg-erro esconder" id="mensagemErro"></span>
      </div>
    </div>
  </form>
</div>
<div class="rodape-modal">
  <button type="button" class="botao" data-form="#formPopup"
    data-processando="Enviando">Enviar</button>
</div>
