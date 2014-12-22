<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<s:eval var="recursoUrl" expression="@environment['site.url.recurso']" scope="request" />
<s:eval var="baseUrl" expression="@environment['site.url.base']" scope="request" />
<s:eval var="imgUrl" expression="@environment['site.url.img']" scope="request" />

<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8">
    <link href="${recursoUrl}/css/reset.css" rel="stylesheet" />
    <link href="${recursoUrl}/css/site.css" rel="stylesheet" />

    <!--[if IE 8]>
      <link href="${recursoUrl}/css/ie8.css" rel="stylesheet" />
    <![endif]-->
    <!--[if lt IE 9]>
      <script src="${recursoUrl}/js/html5shiv-3.6.2.js"></script>
    <![endif]-->

    <title>Oops!</title>
  </head>
  <body>
    <section class="container primario">
      <div class="container-erro">
        <div>
        <img src="${recursoUrl}/img/logo.png" id="logo" />
        </div>
        <div class="mensagem">
          <h1><strong>Oops!</strong> Algo está tecnicamente errado.</h1>
          <p>
            Desculpe, por algum motivo não conseguimos processar sua requisição. Não tema, 
            nós já fomos notificados e vamos verificar para que tudo volte ao normal o mais breve possível. 
            Você pode nos enviar um e-mail <a href="mailto:contato@guiaindicado.com">contato@guiaindicado.com</a> ou
            <a href="http://twitter.com/guiaindicado" target="_blank">tweetar @guiaindicado</a>.
          </p>
          <p><a href="${baseUrl}/">Voltar ao Guia Indicado</a></p>
        </div>
      </div>
    </section>  
  </body>
</html>
