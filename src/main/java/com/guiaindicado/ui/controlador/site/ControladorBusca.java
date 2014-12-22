package com.guiaindicado.ui.controlador.site;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.busca.Busca;
import com.guiaindicado.busca.Busca.Ordem;
import com.guiaindicado.busca.Buscador;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaCidade;
import com.guiaindicado.ui.modelo.Cidade;

/**
 * Processa as requisições referentes a busca. As buscas são basicamente divididas em duas partes:
 * 1) Busca normal pelos termo buscado no campo de busca, no cabeçalho; 2) Pela categoria.
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorBusca extends ControladorBuscaPadrao {

    @Autowired private Buscador buscador;
    @Autowired private PesquisaCidade pesquisaCidade;

    /**
     * Quando nenhum termo é informado o usuário é direcionado a home.
     * 
     * @return Nome da view
     */
    @RequestMapping(value = "/busca", method = RequestMethod.GET)
    public String buscar() {
        return "redirect:/";
    }
    
    /**
     * Processa a busca quando ela parte do campo de busca no cabeçalho. O termo informado é
     * tratado, onde, os caracteres especiais e acentos são removidos. Após esse processamento
     * inicial, o site direciona para quem terá a responsabilidade de buscar.
     * 
     * @param termo Termo que deve ser encontrado
     * @param local Onde a busca ocorrerá (Cidade)
     * @param redirecionamento Dados que serão usados para o direcionamento
     * @param resposta HttpServletRequest para criação do cookie de local
     * @return
     */
    @RequestMapping(value = "/busca", method = RequestMethod.POST)
    public String buscar(@RequestParam(value = "termo", required = false) String termo,
        @RequestParam(value = "local", required = false) String local,
        @CookieValue(value = "ULOCAL", required = false) String cookieLocal,
        RedirectAttributes redirecionamento, HttpServletResponse resposta) {

        if (termo.trim().isEmpty()) {
            return "redirect:/";
        }
        
        int cidadeBusca = NumberUtils.toInt(local);
        int cidadeCookie = NumberUtils.toInt(cookieLocal);
        
        if (cidadeBusca != cidadeCookie) {
            Optional<Cidade> cidade = pesquisaCidade.pesquisarPorId(cidadeBusca);
        
            if (!cidade.isPresent()) {
                criarCookieLocal(cidadeBusca, resposta);
            }
        }
        
        Busca busca = Busca.construtor()
            .comTermo(termo)
            .comLocal(cidadeBusca)
            .construir();
        
        redirecionamento.addAttribute("termoHifenizado", busca.getTermoHifenizado());
        return "redirect:/busca/{termoHifenizado}";
        
    }

    /**
     * Realiza efetivamente a busca de itens disponíveis no site. Caso esse mapeamento
     * seja acessado diretamente, o mesmo tratamento é realizado para a busca através do
     * formulário.
     * 
     * @param termo Termo que deve ser encontrado
     * @param local Onde a busca ocorrerá (Cidade)
     * @param inicio Item inicial para paginação
     * @param ordem Ordem em que a busca deve ser realizada
     * @param modelo Onde os dados serão colocados
     * @return Nome da view
     */
    @RequestMapping(value = "/busca/{termo}", method = RequestMethod.GET)
    public String buscar(@PathVariable(value = "termo") String termo,
        @CookieValue(value = "ULOCAL", required = false) String local,
        @RequestParam(value = "inicio", required = false) String inicio,
        @RequestParam(value = "ordem", required = false) String ordem, Model modelo) {

        Busca busca = Busca.construtor()
            .comTermo(termo)
            .comAgruparCategoria(true)
            .comLocal(NumberUtils.toInt(local))
            .comInicio(NumberUtils.toInt(inicio))
            .comOrdem(ordem)
            .construir();
        
        definirModeloComum(busca, modelo);
        modelo.addAttribute("resultado", buscador.buscar(busca));

        return "busca/resultado";
    }
    
    /**
     * Quando nenhuma categoria for informada, retorna para a home. 
     * 
     * @return Nome da view
     */
    @RequestMapping(value = "/categoria/", method = RequestMethod.GET)
    public String buscarCategoria() {
        return "redirect:/";
    }
    
    /**
     * Busca todos os itens existentes em uma categoria.
     * 
     * @param categoria Categoria a ser filtrada
     * @return Nome da view
     */
    @RequestMapping(value = "/categoria/{categoria}", method = RequestMethod.GET)
    public String buscarCategoria(@PathVariable("categoria") String categoria,
        @CookieValue(value = "ULOCAL", required = false) String local,
        @RequestParam(value = "inicio", required = false) String inicio,
        @RequestParam(value = "ordem", required = false) String ordem, Model modelo) {
        
        Busca busca = Busca.construtor()
            .comCategoria(categoria)
            .comLocal(NumberUtils.toInt(local))
            .comInicio(NumberUtils.toInt(inicio))
            .comOrdem(ordem)
            .construir();
        
        definirModeloComum(busca, modelo);
        modelo.addAttribute("categoriaHifenizada", busca.getCategoriaHifenizada());
        modelo.addAttribute("renderizarCategoriaTodas", false);
        modelo.addAttribute("resultado", buscador.buscar(busca));
        
        return "busca/resultado";
    }
    
    /**
     * Pesquisa o termo informado na busca filtrando-o por categoria.
     * 
     * @param categoria Categoria a ser filtrada
     * @return Nome da view
     */
    @RequestMapping(value = "/busca/{categoria}/{termo}", method = RequestMethod.GET)
    public String buscar(@PathVariable("categoria") String categoria,
        @PathVariable("termo") String termo,
        @CookieValue(value = "ULOCAL", required = false) String local,
        @RequestParam(value = "inicio", required = false) String inicio,
        @RequestParam(value = "ordem", required = false) String ordem, Model modelo) {
        
        Busca busca = Busca.construtor()
            .comCategoria(categoria)
            .comTermo(termo)
            .comLocal(NumberUtils.toInt(local))
            .comInicio(NumberUtils.toInt(inicio))
            .comOrdem(ordem)
            .construir();

        definirModeloComum(busca, modelo);
        modelo.addAttribute("renderizarCategoriaTodas", true);
        modelo.addAttribute("resultado", buscador.buscar(busca));
        
        return "busca/resultado";
    }
    
    /**
     * Define a url de paginação, verificando se é necessário colocar a categoria e/ou a
     * ordem, por exemplo.
     * 
     * @param busca
     * @param modelo
     */
    private void definirModeloComum(final Busca busca, Model modelo) {
        String categoria = busca.getCategoriaHifenizada();
        String termo = busca.getTermoHifenizado();
        String urlPaginacao = "";
        
        if (termo.isEmpty() && !categoria.isEmpty()) {
            urlPaginacao = "categoria/" + categoria;
        } else {
            urlPaginacao = "busca/" + categoria + "/" + termo;
        }
        
        modelo.addAttribute("termoNormalizado", busca.getTermoNormalizado());
        modelo.addAttribute("termoHifenizado", termo);
        modelo.addAttribute("urlPaginacao", urlPaginacao.replaceAll("//", "/"));
        modelo.addAttribute("ordem", busca.getOrdem());
        
        Collection<Ordem> ordenacoes = Lists.newArrayList(Ordem.values());
        
        Iterables.removeIf(ordenacoes, new Predicate<Ordem>() {
            @Override public boolean apply(Ordem ordem) {
                return busca.getOrdem().equals(ordem);
            }
        });
        
        modelo.addAttribute("ordenacoes", ordenacoes);
        
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO, PosicaoBanner.LATERAL);
    }
}
