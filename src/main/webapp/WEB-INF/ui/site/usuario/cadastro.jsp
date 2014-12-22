<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<section class="container primario">
  <div class="acesso-form">
    <h3>Cadastre-se</h3> 
    <form id="formCadastro" class="form" action="${baseUrl}/cadastro" method="post">
      <div class="grupo-controle">
        <label class="rotulo-curto" for="nome">Nome</label>
        <div class="controles-curto">
          <input type="text" id="nome" name="nome" class="span5" data-foco="iniciado" />
          <span class="msg-erro esconder" id="nomeErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-curto" for="email">E-mail</label>
        <div class="controles-curto">
          <input type="text" id="email" name="email" class="span5" />
          <span class="msg-erro esconder" id="emailErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-curto" for="cidade">Cidade</label>
        <div class="controles-curto">
          <input type="text" id="cidade" class="span5" placeholder="Opcional">
          <input type="hidden" id="codigoCidade" name="cidade">
          <span class="msg-erro esconder" id="cidadeErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <label class="rotulo-curto" for="senha">Senha</label>
        <div class="controles-curto">
          <input type="password" id="senha" name="senha" class="span5" />
          <span class="msg-erro esconder" id="senhaErro"></span>
        </div>
      </div>
      <div class="grupo-controle">
        <div class="controles-curto">
          <label class="checkbox" for="termoAceito">
            <input type="checkbox" id="termoAceito" name="termoAceito"> 
              Concordo com os <a href="${baseUrl}/termo" data-alvo="modal">
                Termos de uso</a> do Guia Indicado
            <span class="msg-erro esconder" id="termoAceitoErro"></span>
          </label>
        </div>
      </div>
      <div class="grupo-controle">
        <div class="controles-curto receber-email">
          <label class="checkbox" for="receberEmail">
            <input type="checkbox" id="receberEmail" name="receberEmail" checked="checked"> 
              <span>Aceito receber informações sobre novidades, promoções e ofertas especiais do Guia Indicado e de seus parceiros</span> 
          </label>
        </div>
      </div>
      <div class="grupo-controle acao">
        <div class="controles-curto">
          <button id="confirmar" type="button" class="botao" data-form="#formCadastro"
            data-processando="Cadastrando">Cadastrar</button>
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
   <span class="mensagem">Já possui cadastro? 
    <strong><a href="${baseUrl}/login">Entre</a></strong>
   </span>
 </div>
</section>
