<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="te" uri="http://tiles.apache.org/tags-tiles-extras"%>

<!DOCTYPE html>
<html lang="pt-BR">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${recursoUrl}/css/bootstrap-2.2.1.css" rel="stylesheet" />
    <link href="${recursoUrl}/css/admin.css" rel="stylesheet" />
  
    <!--[if lt IE 9]>
      <script src="${recursoUrl}/js/html5shiv-3.6.2.js"></script>
    <![endif]-->
  
    <title>Guia Indicado | Administração</title>
  </head>
  <body>
    <t:insertAttribute name="navegacao" defaultValue="" />
    <te:useAttribute name="renderizarBarraLateral" />

    <section class="container primario">
      <c:choose>
        <c:when test="${renderizarBarraLateral}">
          <div class="row-fluid">
            <article class="span8">
              <t:insertAttribute name="conteudo" defaultValue="" />
            </article>
            <aside class="span4 well">
              <ul class="nav nav-list">
                <li class="nav-header">List header</li>
                <li><a href="#">Home</a></li>
                <li><a href="#">Library</a></li>
                <li><a href="#">Applications</a></li>
                <li class="nav-header">Another list header</li>
                <li><a href="#">Profile</a></li>
                <li><a href="#">Settings</a></li>
                <li class="divider"></li>
                <li><a href="#">Help</a></li>
              </ul>
            </aside>
          </div>   
        </c:when>
        <c:otherwise>
          <div class="row-fluid">
            <article class="span12">
              <t:insertAttribute name="conteudo" defaultValue="" />
            </article>
          </div> 
        </c:otherwise>
      </c:choose>
    </section>
    <t:insertAttribute name="rodape" defaultValue="" />

    <c:set var="renderizarAlerta" value="${not empty mensagemDirecionada}" />

    <div class="alerta" id="alerta" style="<c:if test="${!renderizarAlerta}">display: none;</c:if>">
      <div class="mensagem">
        <div class="mensagem-interna">
          <span class="texto-mensagem">
            <c:if test="${renderizarAlerta}">${mensagemDirecionada}</c:if>
          </span>
          <a class="dispensar" href="#">×</a>
        </div>
      </div>
    </div>
    
    <script type="text/javascript" src="${recursoUrl}/js/jquery-1.8.2.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/jqueryui-1.9.1.ca.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/jquery-meiomask-1.1.6.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/bootstrap-2.2.1.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/jquery.fileupload-5.19.7.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/jquery.iframe-transport-1.5.js"></script>
    <te:useAttribute name="moduloJs" ignore="true" />
    
    <script type="text/javascript">
      window.$ite = {URL: "${baseUrl}", SURL: "${siteBaseUrl}", MOJ: "${moduloJs}"};
    </script>
    <script type="text/javascript" src="${recursoUrl}/js/require-2.1.2.js"
      data-main="${recursoUrl}/js/admin.js"></script>
</body>
</html>
