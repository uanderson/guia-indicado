<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-inverse navbar-fixed-top">
  <div class="navbar-inner">
    <div class="container">
      <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <div class="nav-collapse">
        <a class="brand" href="${baseUrl}">Home</a>
        <ul class="nav">
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Empresas <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="${baseUrl}/empresas/cadastro">Cadastrar</a></li>
              <li><a href="${baseUrl}/empresas">Consultar</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Destaques <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="${baseUrl}/destaques/cadastro">Cadastrar</a></li>
              <li><a href="${baseUrl}/destaques">Consultar</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Anunciantes <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="${baseUrl}/anunciantes/cadastro">Cadastrar</a></li>
              <li><a href="${baseUrl}/anunciantes">Consultar</a></li>
            </ul>
          </li>
          <li class="dropdown">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">Banners <b class="caret"></b></a>
            <ul class="dropdown-menu">
              <li><a href="${baseUrl}/banners/cadastro">Cadastrar</a></li>
              <li><a href="${baseUrl}/banners">Consultar</a></li>
            </ul>
          </li>
          <li><a href="${baseUrl}/indices">Índices</a></li>
        </ul>
        <ul class="nav pull-right">
          <li><a href="${baseUrl}/logout">Sair</a></li>
        </ul>
      </div>
    </div>
  </div>
</nav>