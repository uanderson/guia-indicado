<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Banners">
  <c:if test="${not empty resultado.colecao}">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>Anunciante</th>
          <th>Posição</th>
          <th>Tipo de media</th>
          <th>Status</th>
          <th class="mini">Ações</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="item" items="${resultado.colecao}">
          <tr>
            <td>${item.nomeAnunciante}</td>
            <td>${item.posicao.descricao}</td>
            <td>${item.tipoMedia}</td>
            <td>
              <c:choose>
                <c:when test="${item.ativo}"><span class="label label-success">Ativo</span></c:when>
                <c:otherwise><span class="label">Inativo</span></c:otherwise>
              </c:choose>
            </td>
            <td class="nowrap centro">
              <a href="${baseUrl}/banners/${item.id}/alteracao" title="Alterar">
                <i class="icon-edit"></i></a>
              <c:if test="${item.ativo}">
                <a href="${baseUrl}/banners/${item.id}/inativacao" data-acao="direta" title="Inativar">
                  <i class="icon-arrow-down"></i></a>
              </c:if>             
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <gt:paginacao url="${baseUrl}/destaques" resultado="${resultado}" />
  </c:if>
  <c:if test="${empty resultado.colecao}">
    <div class="alert alert-info">
      Nenhum destaque cadastrado.
    </div>
  </c:if>
</gt:containerIntitulado>
