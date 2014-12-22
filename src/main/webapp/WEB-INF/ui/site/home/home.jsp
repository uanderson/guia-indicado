<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>

<section class="container primario">
  <article>
    <c:if test="${totalGruposDestaques > 0}">
      <div class="area-destaque">
        <h3 class="ttl">Destaques</h3>
        <div class="destaque">
          <c:forEach var="grupo" items="${gruposDestaque}" varStatus="indice">
            <div class="grupo <c:if test="${indice.count == 1}">ativo</c:if>" 
              <c:if test="${indice.count > 1}">style="display: none"</c:if>>
              <c:forEach var="item" items="${grupo.destaques}" varStatus="indiceDestaque">
                <div class="d${indiceDestaque.count}">
                  <div class="opaco">
                    <a class="img <c:if test="${item.vertical}">vertical</c:if>" 
                        href="${baseUrl}/empresa/${item.referenciaEmpresa}/${item.empresa}">
                      <img src="${imgUrl}/destaque/${item.imagemRelativa}" />
                    </a>
                    <a class="legenda <c:if test="${item.vertical}">vertical</c:if>" 
                        href="${baseUrl}/empresa/${item.referenciaEmpresa}/${item.empresa}">
                      <span class="titulo">${item.titulo}</span>
                      <span class="texto">${item.texto}</span>
                    </a>
                  </div>
                </div>
              </c:forEach>
            </div>
          </c:forEach>
        </div>
        <c:if test="${totalGruposDestaques > 1}">
          <a class="nave" href="#"></a>
          <a class="navd" href="#"></a>
        </c:if>
      </div>
    </c:if>
    <div class="categoria-direta">
      <h2 class="unica">Categorias</h2>
      <c:forEach var="categoriaDireta" items="${categoriasDiretas}">
        <div class="coluna">
          <ul>
            <c:forEach var="categoria" items="${categoriaDireta.categorias}">
              <li><a href="${baseUrl}/categoria/${categoria.referencia}">${categoria.nome}</a></li>
            </c:forEach>
          </ul>
        </div>
      </c:forEach>
    </div>
    <c:if test="${not empty BANNER_HOME}">
      <div class="banner-home ultimo-componente">
        <a href="${banner.url}" rel="nofollow"> <c:choose>
            <c:when test="${BANNER_HOME.tipoMedia.nome == 'SWF'}">
              <object type="application/x-shockwave-flash" width="${BANNER_HOME.posicao.largura}"
                height="${BANNER_HOME.posicao.altura}" 
                data="${imgUrl}/banner/${BANNER_HOME.imagemRelativa}"></object>
            </c:when>
            <c:otherwise>
              <img src="${imgUrl}/banner/${BANNER_HOME.imagemRelativa}" />
            </c:otherwise>
          </c:choose>
        </a>
      </div>
    </c:if>
  </article>
  <aside>
    <c:if test="${totalGruposDestaques > 0}">
      <div class="mais-dez">
        <h3 class="ttl">Os +10 da Semana</h3>
        <ul>
          <c:forEach var="item" items="${dezMais}">
            <li><a href="${baseUrl}/empresa/${item.referencia}/${item.id}">${item.nome}</a></li>
          </c:forEach>
        </ul>
      </div>
    </c:if>
    <t:insertDefinition name="barra/lateral/publicidade" />
    <t:insertDefinition name="barra/lateral/facebook" />
  </aside>
</section>
<c:if test="${not empty anunciantes}">
  <section class="container">
    <div class="anunciantes">
      <h3 class="ttl">Novos Anunciantes</h3>
      <div class="lista">
        <c:forEach var="item" items="${anunciantes}">
          <div>
            <a href="#">
              <img src="${imgUrl}/anunciante/${item.imagemRelativa}" />
              <span class="titulo">${item.nome}</span>
            </a>
          </div>
        </c:forEach>
      </div>
      <a class="nave" href="#"></a>
      <a class="navd" href="#"></a>
    </div>
  </section>
</c:if>
