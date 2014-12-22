<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="${empresa.nome}" subtitulo="Imagens">
  <div class="row-fluid">
    <c:choose>
      <c:when test="${empty imagens}">
        <div class="alert alert-info">Nenhuma imagem cadastrada</div>
      </c:when>
      <c:otherwise>
        <c:set var="criarUl" value="true" />
        <c:forEach var="imagem" items="${imagens}" varStatus="contador">
          <c:if test="${criarUl}">
            <c:set var="criarUl" value="false" />
            <ul class="thumbnails">
          </c:if>
          <li class="span4">
            <div class="thumbnail">
              <img src="${imgUrl}/empresa/${id}/${imagem.imagemRelativaMedia}" alt="">
              <div class="caption">
                <p>
                  <c:choose>
                    <c:when test="${imagem.principal}">
                      <span class="label">Principal</span>  
                    </c:when>
                    <c:otherwise>
                      <a href="${baseUrl}/imagens/${imagem.id}/principal" data-acao="direta" 
                        title="Tornar principal"><i class="icon-play-circle"></i></a>
                    </c:otherwise>
                  </c:choose>
                  <a href="${baseUrl}/imagens/${imagem.id}/exclusao" data-acao="direta" 
                    title="Excluir"><i class="icon-trash"></i></a>
                </p>
              </div>
            </div>
          </li>
          <c:if test="${contador.count % 3 == 0}">
            <c:set var="criarUl" value="true" />
            </ul>
          </c:if>
        </c:forEach>
      </c:otherwise>
    </c:choose>
  </div>
  <form id="form" action="${baseUrl}/empresas/${id}/imagens" method="post" enctype="multipart/form-data">
    <input type="file" id="imagem" name="imagem" multiple="multiple" style="display: none" />
    <div class="form-actions">
      <button type="button" id="adicionar" class="btn btn-primary">Enviar imagens</button>
    </div>
  </form>
</gt:containerIntitulado>
