<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Empresas">
  <c:if test="${not empty resultado.colecao}">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Categoria</th>
          <th>Status</th>
          <th class="mini">Ações</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="item" items="${resultado.colecao}">
          <tr>
            <td>${item.nome}</td>
            <td>${item.nomeCategoria}</td>
            <td>
              <c:choose>
                <c:when test="${item.status == 0}"><span class="label">Pendente</span></c:when>
                <c:when test="${item.status == 1}"><span class="label label-warning">Revisado</span></c:when>
                <c:when test="${item.status == 2}"><span class="label label-success">Moderado</span></c:when>
              </c:choose>
            </td>
            <td class="nowrap centro">
              <a href="${baseUrl}/empresas/${item.id}/alteracao" title="Alterar">
                <i class="icon-edit"></i></a>
              <c:if test="${item.status == 1}">
                <a href="${baseUrl}/empresas/${item.id}/moderacao" data-acao="direta" title="Moderar">
                  <i class="icon-ok"></i></a>
              </c:if>
              <a href="${baseUrl}/empresas/${item.id}/imagens" title="Imagens">
                <i class="icon-picture"></i></a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <gt:paginacao url="${baseUrl}/empresas" resultado="${resultado}" />
  </c:if>
  <c:if test="${empty resultado.colecao}">
    <div class="alert alert-info">
      Nenhuma empresa cadastrada.
    </div>
  </c:if>
</gt:containerIntitulado>
