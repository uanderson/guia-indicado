<%@ page contentType="text/html" pageEncoding="UTF-8"%>

<section class="container primario">
  <div class="form-centralizado">
    <h3>Indique uma empresa</h3>
    <form id="form" class="form" action="${baseUrl}/empresa/indicacao" method="post">
      <fieldset>
        <legend>Sobre a empresa</legend>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="nome">Nome</label>
          <div class="controles-slargo">
            <input type="text" class="span5" id="nome" name="nome" data-foco="iniciado" />
            <span class="msg-erro esconder" id="nomeErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="descricao">Descrição</label>
          <div class="controles-slargo">
            <textarea rows="3" class="span5" id="descricao" name="descricao" placeholder="Opcional"></textarea>
            <span class="msg-erro esconder" id="descricaoErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for=categoria>Categoria</label>
          <div class="controles-slargo">
            <input type="text" id="categoria" name="nomeCategoria" class="span5" />
            <input type="hidden" id="codigoCategoria" name="categoria" />
            <span class="msg-erro esconder" id="nomeCategoriaErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="tags">Palavras-chave</label>
          <div class="controles-slargo">
            <textarea rows="2" class="span5" id="tags" name="tags" placeholder="Opcional"></textarea>
            <span class="msg-erro esconder" id="tagsErro"></span>
          </div>
        </div>
      </fieldset>
      <fieldset>
        <legend>Endereço</legend>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="cep">CEP</label>
          <div class="controles-slargo">
            <input type="text" class="span2" id="cep" name="cep" data-mask="cep" placeholder="Opcional" />
            <span class="msg-erro esconder" id="cepErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="endereco">Endereço</label>
          <div class="controles-slargo">
            <input type="text" class="span5" id="endereco" name="endereco" />
            <span class="msg-erro esconder" id="enderecoErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="numero">Número</label>
          <div class="controles-slargo">
            <input type="text" class="span2" id="numero" name="numero" placeholder="Opcional" />
            <span class="msg-erro esconder" id="numeroErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="bairro">Bairro</label>
          <div class="controles-slargo">
            <input type="text" class="span5" id="bairro" name="bairro" placeholder="Opcional" />
            <span class="msg-erro esconder" id="bairroErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="cidade">Cidade</label>
          <div class="controles-slargo">
            <input type="text" id="cidade" class="span5">
            <input type="hidden" id="codigoCidade" name="cidade" />
            <span class="msg-erro esconder" id="cidadeErro"></span>
          </div>
        </div>
      </fieldset>
      <fieldset>
        <legend>Contato</legend>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="telefone">Telefone</label>
          <div class="controles-slargo">
            <input type="text" class="span2" id="telefone" name="telefone" data-mask="phone">
            <span class="msg-erro esconder" id="telefoneErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="celular">Celular</label>
          <div class="controles-slargo">
            <input type="text" class="span2" id="celular" name="celular" data-mask="phone" placeholder="Opcional">
            <span class="msg-erro esconder" id="celularErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="email">Email</label>
          <div class="controles-slargo">
            <input type="text" class="span5" id="email" name="email" placeholder="Opcional">
            <span class="msg-erro esconder" id="emailErro"></span>
          </div>
        </div>
        <div class="grupo-controle">
          <label class="rotulo-slargo" for="site">Site</label>
          <div class="controles-slargo">
            <input type="text" class="span5" id="site" name="site" placeholder="Opcional">
            <span class="msg-erro esconder" id="siteErro"></span>
          </div>
        </div>
      </fieldset>
      <div class="grupo-controle acao">
        <div class="controles-slargo">
          <button id="confirmar" type="button" class="botao" data-form="#form"
            data-processando="Indicando">Indicar</button>
        </div>
      </div>
    </form>
  </div>
</section>