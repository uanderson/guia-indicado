<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<section class="container primario">
  <div class="acesso-form">
    <h3>Entre</h3>
    <c:if test="${not empty mensagemErro}">
      <div class="mensagem-erro-login">${mensagemErro}</div>
    </c:if>
    <form id="form" class="form" action="${baseUrl}/j_spring_security_check" method="post">
      <div class="grupo-controle">
        <label class="rotulo-curto" for="j_username">E-mail</label>
        <div class="controles-curto">
          <input type="text" class="span5" id="j_username" name="j_username" data-foco="iniciado" />
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-curto" for="j_password">Senha</label>
        <div class="controles-curto">
          <input type="password" class="span5" id="j_password" name="j_password" />
        </div>
      </div>
      <div class="grupo-controle">
        <div class="controles-curto">
          <a href="${baseUrl}/senha/esqueci" data-alvo="modal">Esqueceu sua senha?</a>
        </div>
      </div>
      <div class="grupo-controle">
        <div class="controles-curto">
          <label for="j_remember" class="checkbox">
            <input type="checkbox" name="_spring_security_remember_me" id="_spring_security_remember_me" /> 
              Mantenha-me conectado
          </label>
        </div>
      </div>
      <div class="grupo-controle acao">
        <div class="controles-curto">
            <button id="confirmar" type="submit" class="botao">Confirmar</button>
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
   <span class="mensagem">
    Não tem cadastro? <strong><a href="${baseUrl}/cadastro">Cadastre-se</a></strong>
   </span>
 </div>
</section>
