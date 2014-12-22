<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>
<%@ taglib prefix="gn" uri="http://guiaindicado.com/jsp/jstl/funcoes"%>

<section class="container primario">
  <article>
    <c:choose>
      <c:when test="${resultado.total > 0}">
        <div class="resultado">
          <div class="ttl">
            <span>Resultados <small> / Página ${resultado.paginaCorrente + 1} 
              de ${resultado.total} resultados</small></span>
            <div class="ordem" id="ordem">
              <span>${ordem.descricao}</span> 
              <img src="${recursoUrl}/img/setas/filtro.png" />
            </div>
            <ul id="opcoesOrdem">
              <c:forEach var="item" items="${ordenacoes}">
                <li><a href="${baseUrl}/${urlPaginacao}/?inicio=0&ordem=${item.id}">
                    ${item.descricao}</a></li>
              </c:forEach>            
            </ul>
          </div>
        </div>
        <ol class="listagem">
          <c:forEach var="item" items="${resultado.colecao}">
            <li>
              <img class="img" src="${imgUrl}/${item.imagemRelativaBusca}" width="117" height="92" />
              <div class="descricao">
                <h4>${item.nome}</h4>
                <div class="dados-item">
                  <span>${item.endereco}<c:if test="${not empty item.numero}">, ${item.numero}</c:if></span>
                  <span>${item.bairro} / <strong>${gn:formatarTelefone(item.telefone)}</strong></span>
                </div>
              </div>
              <div class="detalhe">
                <div class="avaliacao">
                  <c:forEach begin="1" end="${item.mediaAvaliacao}">                
                    <div class="estrela"></div>
                  </c:forEach>
                  <c:forEach begin="${item.mediaAvaliacao}" end="4">                
                    <div class="estrela-inativa"></div>
                  </c:forEach>
                </div>
                <div class="total">(${item.avaliacoes}) avaliações</div>
                <a class="botao" href="${baseUrl}/${item.urlDetalheItem}">Visualizar</a>
              </div>
            </li>
          </c:forEach>
        </ol>
        <gt:paginacaoBusca resultado="${resultado.resultado}" ordem="${ordem.id}" />
      </c:when>
      <c:otherwise>
        <div class="sem-resultado">
          <h4>Sua busca <c:if test="${not empty termoNormalizado}">por "${termoNormalizado}"</c:if> 
            não encontrou nenhum item correspondente</h4>
          <p>Sugestões para a busca:</p>
          <ul>
            <li>Certifique-se de que todas as palavras estejam escritas corretamente</li>
            <li>Tente palavras-chave diferentes</li>
            <li>Tente palavras-chave mais genéricas</li>
            <li>Tente usar menos palavras-chave</li>
          </ul>
        </div>
        <div class="sem-resultado mensagem">
          Gostaria de ver uma empresa que você conhece no Guia Indicado? 
            <a href="${baseUrl}/empresa/indicacao">Indique.</a> É gratuito!
        </div>
      </c:otherwise>
    </c:choose>
  </article>
  <aside>
    <c:choose>
      <c:when test="${not empty resultado.categorias}">
        <div class="filtro-busca">
          <h3 class="ttc">Filtrar por categoria</h3>
          <ul>
            <c:forEach var="categoria" items="${resultado.categorias}">
              <li>
                <a href="${baseUrl}/busca/${categoria.referencia}/${termoHifenizado}">${categoria.nome}</a>
                <span>${categoria.total}</span>
              </li>
            </c:forEach>
          </ul>
        </div>
      </c:when>
      <c:when test="${resultado.total > 0 && renderizarCategoriaTodas}">
        <div class="filtro-busca">
          <h3 class="ttc">Filtrar por categoria</h3>
          <ul>
              <li>
                <a href="${baseUrl}/busca/${termoHifenizado}">Todas</a>
              </li>
          </ul>
        </div>
      </c:when>
    </c:choose>
    <t:insertDefinition name="barra/lateral/publicidade" />
    <c:if test="${resultado.total > 0}">
      <t:insertDefinition name="barra/lateral/facebook" />
    </c:if>
  </aside>      
</section>