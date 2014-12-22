<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="${recursoUrl}/css/bootstrap-2.2.1.css" rel="stylesheet" />
    <link href="${recursoUrl}/css/admin.css" rel="stylesheet" />
  
    <!--[if lt IE 9]>
      <script src="${recursoUrl}/js/html5shiv-3.6.2.js"></script>
    <![endif]-->
  
    <title>Guia Indicado | Login</title>
  </head>
  <body>
    <div class="login-container">
      <div class="block small center login">
        <div class="block-head">
          <div class="bheadl"></div>
          <div class="bheadr"></div>
          <h2>Acesso</h2>
        </div>
        <div class="block-content">
          <c:if test="${not empty mensagemErro}">
            <div class="alert alert-error">${mensagemErro}</div>
          </c:if>
          <form action="${baseUrl}/j_spring_security_check" method="post">
            <div class="row-fluid">
              <label for="j_username">E-mail</label>
              <input type="text" class="span12" id="j_username" name="j_username" />
            </div>
            <div class="row-fluid">
              <label for="j_password">Senha</label>
              <input type="password" class="span12" id="j_password" name="j_password" />
            </div>
            <div class="form-actions">
              <div class="row-fluid">
                <p><button type="submit" class="btn btn-primary">Efetuar login</button></p>
              </div>
            </div>
          </form>
        </div>
        <div class="bendl"></div>
        <div class="bendr"></div>
      </div>
      <p class="footer">
        <small>Precisa de Ajuda? <a href="mailto:contato@guiaindicado.com">Mande-nos um e-mail</a></small>
      </p>
    </div>
    
    <script type="text/javascript" src="${recursoUrl}/js/jquery-1.8.2.js"></script>
    <script type="text/javascript">$("#j_username").focus();</script>
  </body>
</html>