<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gn" uri="http://guiaindicado.com/jsp/jstl/funcoes" %>

<section class="container primario">
  <div class="form-centralizado">
    <h3>Altere seus dados</h3>
    <form id="form" class="form" action="${baseUrl}/usuario/alteracao" method="post">
      <div class="grupo-controle">
        <label class="rotulo-slargo" for="nome">Nome</label>
        <div class="controles-slargo">
          <input type="text" id="nome" name="nome" class="span5" value="${usuario.nome}"
            data-foco="iniciado" />
          <span class="msg-erro esconder" id="nomeErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-slargo" for="email">E-mail</label>
        <div class="controles-slargo">
          <input type="text" id="email" name="email" class="span5" value="${usuario.email}"
            disabled="disabled" />
          <span class="msg-erro esconder" id="emailErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-slargo" for="cidade">Cidade</label>
        <div class="controles-slargo">
          <input type="text" id="cidade" class="span5" placeholder="Opcional"
            value="${gn:formatarCidade(usuario.nomeCidade, usuario.siglaEstado)}" />
          <input type="hidden" id="codigoCidade" name="cidade" value="${usuario.cidade}" />
          <span class="msg-erro esconder" id="cidadeErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <div class="controles-slargo" style="width: 390px;">
          <label class="checkbox" for="receberEmail">
            <input type="checkbox" id="receberEmail" name="receberEmail" 
              <c:if test="${usuario.receberEmail}">checked="checked"</c:if> /> 
              Aceito receber informações sobre novidades, promoções e ofertas especiais do Guia Indicado e de seus parceiros 
          </label>
        </div>
      </div>
      <div class="grupo-controle acao">
        <div class="controles-slargo">
          <button id="confirmar" type="button" class="botao" data-form="#form"
            data-processando="Confirmando">Confirmar</button>
        </div>
      </div>
    </form>
  </div>
</section>