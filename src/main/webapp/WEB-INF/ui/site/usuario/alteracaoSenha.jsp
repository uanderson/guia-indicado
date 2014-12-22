<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:eval var="baseUrl" expression="@environment['site.url.base']" scope="request" />

<div class="cabecalho-modal">
  <button type="button" class="fechar" data-esconder="modal">&times;</button>
  <h2>Alteração de senha</h2>
</div>
<div class="corpo-modal alterar-senha">
  <form id="formPopup" class="form" action="${baseUrl}/senha/alteracao" method="post">
    <div class="grupo-controle">
      <label class="rotulo-largo" for="poSenhaAtual">Senha atual</label>
      <div class="controles-largo">
        <input type="password" id="poSenhaAtual" name="senhaAtual" class="span4" data-foco="iniciado" />
        <span class="msg-erro esconder" id="senhaAtualErro"></span>
      </div>
    </div>
    <div class="grupo-controle">
      <label class="rotulo-largo" for="poSenha">Nova senha</label>
      <div class="controles-largo">
        <input type="password" id="poSenha" name="senha" class="span4" />
        <span class="msg-erro esconder" id="senhaErro"></span>
      </div>
    </div>
    <div class="grupo-controle">
      <label class="rotulo-largo" for="poConfirmacao">Confirmação</label>
      <div class="controles-largo">
        <input type="password" id="poConfirmacao" name="confirmacao" class="span4" />
        <span class="msg-erro esconder" id="confirmacaoErro"></span>
      </div>
    </div>
  </form>
</div>
<div class="rodape-modal">
  <button type="button" class="botao" data-form="#formPopup"
    data-processando="Alterando">Alterar</button>
</div>