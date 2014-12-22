<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<nav>
  <div class="container">
    <ul class="nav">
     <li><a href="${baseUrl}/empresa/indicacao">Indique uma empresa</a></li>
      <sec:authorize access="isAnonymous()">
        <li><a href="${baseUrl}/cadastro">Cadastre-se</a></li>
      </sec:authorize>
      <li>
        <sec:authorize access="isAuthenticated()">
          <a id="entrar" class="logado" href="#"><sec:authentication property="principal.nome" />
            <img src="${recursoUrl}/img/setas/entrar.png" /></a>
        </sec:authorize> <sec:authorize access="isAnonymous()">
          <a id="entrar" href="${baseUrl}/login">Entrar</a>
        </sec:authorize></li>
    </ul>
    <sec:authorize access="isAuthenticated()">
      <div id="navMenu">
        <span class="nome">
          <sec:authentication property="principal.nome" />
            <img src="${recursoUrl}/img/setas/entrar-cima.png" />
        </span>
        <ul>
        <li class="sep"></li>
        <li><a href="${baseUrl}/senha/alteracao" data-alvo="modal">Alterar Senha</a></li>
        <li><a href="${baseUrl}/usuario/alteracao">Alterar Dados</a></li>
        <li class="div"></li>
        <li><a href="${baseUrl}/logout">Sair</a></li>
      </ul>
      </div>
    </sec:authorize>
  </div>
</nav>
