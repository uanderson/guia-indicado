package com.guiaindicado.ui.controlador.site;

import java.util.Collection;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.guiaindicado.busca.Busca;
import com.guiaindicado.busca.Busca.Ordem;
import com.guiaindicado.busca.Buscador;
import com.guiaindicado.dominio.publicidade.PosicaoBanner;
import com.guiaindicado.pesquisa.PesquisaEmpresa;
import com.guiaindicado.pesquisa.PesquisaImagem;
import com.guiaindicado.ui.modelo.Empresa;
import com.guiaindicado.ui.modelo.Imagem;
import com.guiaindicado.ui.modelo.ItemBusca;
import com.guiaindicado.ui.modelo.ResultadoBusca;

/**
 * Processa as requisições relativas ao detalhe da empresa/produto. 
 * 
 * @author Uanderson Soares Gonçalves
 */
@Controller
public class ControladorDetalhe extends ControladorBuscaPadrao {

    @Autowired private Buscador buscador;
    @Autowired private PesquisaEmpresa pesquisaEmpresa;
    @Autowired private PesquisaImagem pesquisaImagem;
    
    @Value("#{environment['site.url.img']}") String imgUrl;
    @Value("#{environment['site.url.base']}") String baseUrl;
    
    /**
     * Detalha a empresa carregando os dados necessários.
     * 
     * @param referencia String hifenizada com nome da empresa
     * @param id ID da empresa
     * @return Nome da view
     */
    @RequestMapping(value = "/empresa/{referencia}/{id}", method = RequestMethod.GET)
    public String detalhe(@PathVariable String referencia, @PathVariable String id, Model modelo) {
        int idEmpresa = NumberUtils.toInt(id);
        Optional<Empresa> empresa = pesquisaEmpresa.pesquisarDetalhe(idEmpresa, referencia);
        
        if (!empresa.isPresent()) {
            return "redirect:/";
        }
        
        Empresa detalhe = empresa.get();
        Collection<Imagem> imagens = pesquisaImagem.pesquisarPorEmpresa(idEmpresa);
        
        if (imagens.size() > 0) {
            Imagem imagemPrincipal = Iterables.find(imagens, new Predicate<Imagem>() {
                @Override public boolean apply(Imagem imagem) {
                    return imagem.getPrincipal();
                }
            });
            
            modelo.addAttribute("imagemPrincipal", imagemPrincipal);
            detalhe.setReferenciaImagem(imagemPrincipal.getNome());
            detalhe.setImagemPrincipal(String.valueOf(imagemPrincipal.getId()));
        }
        
        modelo.addAttribute("imagens", imagens);
        modelo.addAttribute("item", detalhe);
        
        buscarRelacionados(detalhe, modelo);
        carregarBase(modelo);
        carregarBanners(modelo, PosicaoBanner.TOPO, PosicaoBanner.LATERAL);
        carregarMetaTags(modelo, detalhe);
        
        return "detalhe";
    }
    
    /**
     * Busca itens indicados baseado no item detalhado. 
     * 
     * @param empresa Empresa a ser buscada
     * @param modelo Modelo para view
     */
    private void buscarRelacionados(final Empresa empresa, Model modelo) {
        Busca busca = Busca.construtor()
            .comCategoria(empresa.getReferenciaCategoria())
            .comLocal(empresa.getCidade())
            .comOrdem(Ordem.MELHOR_CLASSIFICADO)
            .construir();
        
        ResultadoBusca resultado = buscador.buscar(busca);
        Collection<ItemBusca> colecao = Lists.newArrayList(resultado.getColecao());
        
        Iterables.removeIf(colecao, new Predicate<ItemBusca>() {
            @Override public boolean apply(ItemBusca item) {
                return empresa.getId().equals(item.getId());
            }
        });
        
        modelo.addAttribute("indicados", ImmutableList.copyOf(Iterables.limit(colecao, 3)));
    }
    
    /**
     * Cria as tags open graph do Facebook.
     * 
     * @param modelo Modelo para a view
     */
    private void carregarMetaTags(Model modelo, Empresa empresa) {
        String url = baseUrl + "/empresa/" + empresa.getReferencia() + "/" + empresa.getId();
        String img = imgUrl + "/" + empresa.getImagemRelativa();
        
        StringBuilder sb = new StringBuilder();
        gerarMetaTag(sb, "og:type", "empresa");
        gerarMetaTag(sb, "og:title", empresa.getNome());
        gerarMetaTag(sb, "og:description", empresa.getDescricao());
        gerarMetaTag(sb, "og:url", url);
        gerarMetaTag(sb, "og:image", img);
        
        modelo.addAttribute("metaTags", sb.toString());
    }
    
    /**
     * Adiciona uma metatag a um StringBuilder.
     * 
     * @param sb StringBuilder para montagem
     * @param tipo Tipo da tag property, 
     * @param nome Nome da propriedade da tag
     * @param valor Valor da propriedade da tag
     */
    private void gerarMetaTag(StringBuilder sb, String nome, String valor) {
        sb.append("<meta property=\"");
        sb.append(nome);
        sb.append("\" content=\"");
        sb.append(valor);
        sb.append("\" />");
    }
}
