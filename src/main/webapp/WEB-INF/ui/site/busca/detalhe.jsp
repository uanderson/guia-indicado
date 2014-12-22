<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="gn" uri="http://guiaindicado.com/jsp/jstl/funcoes"%>

<section class="container primario">
  <article>
    <div class="detalhe-item">
      <h3 class="ttl">${item.nome}</h3>
      <div class="info">
        <img class="img" id="imagemPrincipal" src="${imgUrl}/${item.imagemRelativa}" />
        <div class="detalhe">
          <div class="avalie">
            <div class="titulo">
              <span>Faça sua avaliação</span>
              <sec:authorize access="isAnonymous()">
                <span class="logado">Você precisa estar <a href="${baseUrl}/login">logado</a></span>
              </sec:authorize>
            </div>
            <div class="avaliacao" id="avaliacao" data-url="${baseUrl}/empresa/${item.id}/avaliacao">
              <div class="estrelas <sec:authorize access="isAnonymous()">off</sec:authorize>">
                <c:forEach begin="1" end="${item.mediaAvaliacao}">
                  <div class="estrela inativa avaliada"></div>
                </c:forEach>
                <c:forEach begin="${item.mediaAvaliacao}" end="4">
                  <div class="estrela inativa"></div>
                </c:forEach>
              </div>
              <div class="total">(${item.avaliacoes}) avaliações</div>
            </div>
          </div>
          <div class="social">
            <iframe class="facebook" scrolling="no" frameborder="0"  allowTransparency="true" src="//www.facebook.com/plugins/like.php?href=${baseUrl}/empresa/${item.referencia}/${item.id}&send=false&layout=button_count&width=90&show_faces=false&font=arial&colorscheme=light&action=like&height=21" style="border:none; overflow:hidden; width:90px; height:21px;"></iframe>
            <iframe class="twitter" scrolling="no" frameborder="0"  allowTransparency="true" src="http://platform.twitter.com/widgets/tweet_button.html?url=${baseUrl}/empresa/${item.referencia}/${item.id}&via=guiaindicado&text=${item.nome}&count=horizontal&lang=pt" style="border:none; overflow:hidden; width:90px; height:21px; margin-left: 6px;"></iframe>
            <iframe class="gplus" scrolling="no" frameborder="0"  allowTransparency="true" src="https://plusone.google.com/_/+1/fastbutton?bsv=m&size=medium&hl=en-US&origin=http://guiaindicado.com&url=${baseUrl}/empresa/${item.referencia}/${item.id}&ic=1&jsh=m%3B%2F_%2Fscs%2Fapps-static%2F_%2Fjs%2Fk%3Doz.gapi.en.F8QeWlsP6AI.O%2Fm%3D__features__%2Fam%3DAQ%2Frt%3Dj%2Fd%3D1%2Frs%3DAItRSTNCQVyAzFN_rVMDRb_ehTKDpHq_TA#_methods=onPlusOne%2C_ready%2C_close%2C_open%2C_resizeMe%2C_renderstart%2Concircled&id=I0_1354754152967&parent=http://guiaindicado.com" style="border:none; overflow:hidden; width:90px; height:21px; margin-left: 10px;"></iframe>
          </div>
          <p class="texto">${item.descricao}</p>
        </div>
      </div>
      <%-- 
      <c:if test="${not empty imagens && imagens.size() > 1}">
        <div class="miniaturas">
          <c:forEach var="imagem" items="${imagens}">
            <img src="${imgUrl}/empresa/${item.id}/${imagem.imagemRelativaPequena}" />
          </c:forEach>
        </div>
      </c:if> --%>
    </div>
    <c:if test="${not empty indicados}">
      <div class="divisao-detalhe-item"></div>
      <div>
        <h3 class="ttl">Indicado também</h3>
        <ol class="listagem">
          <c:forEach var="item" items="${indicados}">
              <li>
                <c:choose>
                  <c:when test="${not empty item.imagemPrincipal}">
                    <img class="img" src="${imgUrl}/${item.tipo}/${item.id}/${item.imagemPrincipal}-b.jpg"
                      width="117" height="92" />
                  </c:when>
                  <c:otherwise>
                    <img class="img" src="${imgUrl}/sem-imgb.jpg" />
                  </c:otherwise>
                </c:choose>
                <div class="descricao">
                  <h4>${item.nome}</h4>
                  <div class="dados-item">
                    <span>${item.endereco}<c:if test="${not empty item.numero}">, ${item.numero}</c:if></span>
                    <span>${item.bairro} / <strong>${gn:formatarTelefone(item.telefone)}</strong></span>
                  </div>
                </div>
                <div class="detalhe">
                  <div class="avaliacao">
                    <c:forEach begin="1" end="${item.mediaAvaliacao}">                
                      <div class="estrela"></div>
                    </c:forEach>
                    <c:forEach begin="${item.mediaAvaliacao}" end="4">                
                      <div class="estrela-inativa"></div>
                    </c:forEach>
                  </div>
                  <div class="total">(${item.avaliacoes}) avaliações</div>
                  <a class="botao" href="${baseUrl}/${item.tipo}/${item.referencia}/${item.id}">Visualizar</a>
                </div>
              </li>
            </c:forEach>
        </ol>
      </div>
    </c:if>
  </article>
  <aside>
    <div class="contato-detalhe">
      <h3 class="ttc">Contato</h3>
      <div class="bloco">
        <div class="info">
          <span>${gn:formatarTelefone(item.telefone)}</span>
          <c:if test="${not empty item.celular}">
            <span>${gn:formatarTelefone(item.celular)}</span>
          </c:if>
          <c:if test="${not empty item.email}">
            <span>${item.email}</span>
          </c:if>
          <c:if test="${not empty item.site}">
            <a href="${item.site}" target="_blank">${item.site}</a>
          </c:if>
        </div>
        <div class="endereco">
          <span>${item.endereco}, ${item.numero}</span>
          <span>${item.bairro}</span>
        </div>
        <c:if test="${item.latitude != 0.0 && item.longitude != 0.0}">
          <div class="mapa">
            <img src="http://maps.googleapis.com/maps/api/staticmap?&markers=color:blue|label:G|${item.latitude},${item.longitude}&zoom=15&size=256x116&sensor=false" />
          </div>
        </c:if>
      </div>
    </div>
    <t:insertDefinition name="barra/lateral/publicidade" />
    <t:insertDefinition name="barra/lateral/facebook" />
  </aside>      
</section>