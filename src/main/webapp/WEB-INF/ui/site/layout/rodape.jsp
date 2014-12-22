<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<footer>
  <div class="container">
    <div class="nav">
      <ul class="info">
        <li><a href="${baseUrl}/empresa" data-alvo="modal">Empresa</a></li>
        <li><a href="${baseUrl}/contato" data-alvo="modal">Contato</a></li>
        <li><a href="${baseUrl}/termo" data-alvo="modal">Avisos legais e termos de uso e privacidade</a></li>
      </ul>
      <ul class="social">
        <li><a href="https://www.facebook.com/guiaindicado" class="facebook" target="_blank">Facebook</a></li>
        <li><a href="http://plus.google.com/b/112535796644196432029/112535796644196432029" class="gplus" target="_blank">Google</a></li>
        <li><a href="https://www.twitter.com/guiaindicado" class="twitter" target="_blank">Twitter</a></li>
      </ul>
    </div>
    <div class="div"></div>
    <div class="direito">&copy; Copyright <fmt:formatDate value="${dataCorrente}" pattern="yyyy" />  
      - Guia Indicado - Todos os direitos reservados</div>
  </div>
</footer>
