<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="gt" uri="http://guiaindicado.com/jsp/jstl/tags"%>

<gt:containerIntitulado titulo="Indexação">
  <div class="row-fluid">
    <div class="span6">
      <div class="span11">
        <h3>Busca</h3>
        <p>Apaga todas os itens de busca e indexa-os novamente.</p>
        <p>
          <form id="formIndiceBusca" action="${baseUrl}/indices/busca" method="post"></form>
          <button type="button" class="btn btn-large btn-warning" data-form="#formIndiceBusca" 
            data-loading-text="Indexando">Indexar</button>
        </p>
      </div>
    </div>
    <div class="span6">
      <div class="span11">
        <h3>Cidades</h3>
        <p>Apaga todas as cidades e indexa-as novamente.</p>
        <p>
          <form id="formIndiceCidade" action="${baseUrl}/indices/cidade" method="post"></form>
          <button type="button" class="btn btn-large btn-warning" data-form="#formIndiceCidade" 
            data-loading-text="Indexando">Indexar</button>
        </p>
      </div>
    </div>
  </div>
</gt:containerIntitulado>