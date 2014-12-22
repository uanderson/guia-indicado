<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty BANNER_LATERAL}">
  <div class="publicidade-lateral">
    <h4>Publicidade</h4>
    <a href="${BANNER_LATERAL.url}" rel="nofollow">
      <c:choose>
        <c:when test="${BANNER_LATERAL.tipoMedia.nome == 'SWF'}">
          <object type="application/x-shockwave-flash" width="${BANNER_LATERAL.posicao.largura}"
            height="${BANNER_LATERAL.posicao.altura}" 
            data="${imgUrl}/banner/${BANNER_LATERAL.imagemRelativa}"></object>
        </c:when>
        <c:otherwise>
          <img src="${imgUrl}/banner/${BANNER_LATERAL.imagemRelativa}" />
        </c:otherwise>
      </c:choose>
    </a>
  </div>
</c:if>