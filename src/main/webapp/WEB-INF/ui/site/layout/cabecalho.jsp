<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="te" uri="http://tiles.apache.org/tags-tiles-extras"%>

<te:useAttribute name="renderizarBusca" />

<header class="container">
  <div class="primario">
    <a class="logo" href="${baseUrl}/">
      <img src="${recursoUrl}/img/logo.png" />
    </a>
    <c:if test="${not empty BANNER_TOPO}">
      <div class="banner-home ultimo-componente">
        <a href="${banner.url}" rel="nofollow"> <c:choose>
            <c:when test="${BANNER_TOPO.tipoMedia.nome == 'SWF'}">
              <object type="application/x-shockwave-flash" width="${BANNER_TOPO.posicao.largura}"
                height="${BANNER_TOPO.posicao.altura}" 
                data="${imgUrl}/banner/${BANNER_TOPO.imagemRelativa}"></object>
            </c:when>
            <c:otherwise>
              <img src="${imgUrl}/banner/${BANNER_TOPO.imagemRelativa}" />
            </c:otherwise>
          </c:choose>
        </a>
      </div>
    </c:if>
  </div>
  <div class="div"></div>
  <c:if test="${renderizarBusca}">
    <div class="busca">
      <div class="be"></div>
      <div class="ce">
        <form action="${baseUrl}/busca" method="post">
          <div class="termo">
            <input type="text" name="termo" autocomplete="off" data-foco="iniciado"
              placeholder="O que você procura?" value="${termoNormalizado}" maxlength="200" />
            <input type="image" src="${recursoUrl}/img/indicador-busca.png" 
              title="Clique aqui para realizar a busca" />
          </div>
          <div class="cidade" tabindex="0">
            <div class="be"></div>
            <div class="ce">
              <span class="selecionada">${cidadeBusca.nome} (${cidadeBusca.siglaEstado})</span>
              <input type="hidden" id="local" name="local" value="${cidadeBusca.id}" />
              <div class="div"></div>
              <c:choose>
                <c:when test="${empty cidadesIndicadas}">
                  <div class="sem-selecao"></div>
                </c:when>
                <c:otherwise>
                  <div class="selecao"></div>
                </c:otherwise>
              </c:choose>
              <ul class="lista">
                <c:forEach var="item" items="${cidadesIndicadas}">
                  <li data-valor="${item.id}">>${item.nome}</li>
                </c:forEach>
              </ul>
            </div>
            <div class="bd"></div>
          </div>
        </form>
      </div>
      <div class="bd"></div>
    </div>
  </c:if>
</header>
