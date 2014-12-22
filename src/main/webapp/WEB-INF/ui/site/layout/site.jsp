<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="te" uri="http://tiles.apache.org/tags-tiles-extras"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <c:out value="${metaTags}" default="" escapeXml="false" />
    <link href="${recursoUrl}/css/reset.css" rel="stylesheet" />
    <link href="${recursoUrl}/css/site.css" rel="stylesheet" />

    <!--[if IE 8]>
      <link href="${recursoUrl}/css/ie8.css" rel="stylesheet" />
    <![endif]-->
    <!--[if lt IE 9]>
      <script src="${recursoUrl}/js/html5shiv-3.6.2.js"></script>
    <![endif]-->

    <title>Guia Indicado | Site de buscas locais</title>
  </head>
  <body>
    <script type="text/javascript">
      var _gaq = _gaq || [];
      _gaq.push(['_setAccount', 'UA-36888912-1']);
      _gaq.push(['_trackPageview']);
    
      (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
      })();
    </script>
  
    <t:insertAttribute name="navegacao" defaultValue="" />
    <t:insertAttribute name="cabecalho" defaultValue="" />
    <t:insertAttribute name="conteudo" defaultValue="" />
    <t:insertAttribute name="rodape" defaultValue="" />

    <c:set var="renderizarAlerta" value="${not empty mensagemDirecionada}" />

    <div class="modal" id="modal" tabindex="-1" style="display: none"></div>
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
    <script type="text/javascript" src="${recursoUrl}/js/jqueryui-1.9.1.cs.js"></script>
    <script type="text/javascript" src="${recursoUrl}/js/jquery-meiomask-1.1.6.js"></script>
    <te:useAttribute id="moduloJs" name="moduloJs" ignore="true" />

    <script type="text/javascript">
      window.$ite = {URL: "${baseUrl}", MOJ: "${moduloJs}", DLG: "${abrirDialogo}"};
    </script>
    <script type="text/javascript" src="${recursoUrl}/js/require-2.1.2.js"
      data-main="${recursoUrl}/js/site.js"></script>
  </body>
</html>
