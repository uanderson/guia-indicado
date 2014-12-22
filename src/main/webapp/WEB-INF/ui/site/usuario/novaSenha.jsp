<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<section class="container primario">
  <div class="acesso-form">
    <h3>Escolha sua nova senha</h3> 
    <form id="formNovaSenha" class="form" action="${baseUrl}/senha/reiniciar" method="post">
      <input type="hidden" name="token" value="${token}" />
      <div class="grupo-controle">
        <label class="rotulo-xlargo" for="senha">Nova senha</label>
        <div class="controles-xlargo">
          <input type="password" id="senha" name="senha" class="span4" data-foco="iniciado" />
          <span class="msg-erro esconder" id="senhaErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-xlargo" for="confirmacao">Confirmação</label>
        <div class="controles-xlargo">
          <input type="password" id="confirmacao" name="confirmacao" class="span4" />
          <span class="msg-erro esconder" id="confirmacaoErro"></span>
        </div>
      </div>
      <div class="grupo-controle acao">
        <div class="controles-xlargo">
          <button id="confirmar" type="button" class="botao" data-form="#formNovaSenha"
            data-processando="Confirmando">Confirmar</button>
        </div>
      </div>
    </form>
  </div>
  <div class="acesso-info">
    <h1>Faça parte do Guia Indicado</h1>
    <ul class="adicoes">
      <li>Indique suas empresas favoritas</li>
      <li>Avalie as empresas indicadas no site</li>
      <li>Receba ofertas exclusivas</li>
      <li>Compartilhe</li>
   </ul>
 </div>
</section>
