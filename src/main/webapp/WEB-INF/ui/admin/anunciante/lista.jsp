<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Anunciantes">
  <c:if test="${not empty resultado.colecao}">
    <table class="table table-hover">
      <thead>
        <tr>
          <th>Nome</th>
          <th class="mini">Ações</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="item" items="${resultado.colecao}">
          <tr>
            <td>${item.nome}</td>
            <td class="nowrap centro">
              <a href="${baseUrl}/anunciantes/${item.id}/alteracao" title="Alterar">
                <i class="icon-edit"></i></a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
    <gt:paginacao url="${baseUrl}/anunciantes" resultado="${resultado}" />
  </c:if>
  <c:if test="${empty resultado.colecao}">
    <div class="alert alert-info">
      Nenhum anunciante cadastrado.
    </div>
  </c:if>
</gt:containerIntitulado>
