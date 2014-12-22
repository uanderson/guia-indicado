package com.guiaindicado.ui.tags;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.guiaindicado.servico.email.EntregadorEmail;

/**
 * Tag responsável por gerar o HTML para a paginação.
 * 
 * @author Uanderson Soares Gonçalves
 */
@SuppressWarnings("serial")
public class ContainerIntitulado extends TagSupport {

    private static final Logger LOG = LoggerFactory.getLogger(EntregadorEmail.class);
    
    private String titulo;
    private String subtitulo;
    
    /**
     * Renderiza o ínicio da tag.
     * 
     * @see TagSupport#doStartTag()
     */
    @Override
    public int doStartTag() throws JspException {
        StringBuilder html = new StringBuilder();
        html.append("<div class=\"block\">");
        html.append("<div class=\"block-head\">");
        html.append("<div class=\"bheadl\"></div>");
        html.append("<div class=\"bheadr\"></div>");
        html.append("<h2>");
        html.append(titulo);
        
        if (subtitulo != null && !subtitulo.isEmpty()) {
            html.append(" <small>");
            html.append(subtitulo);
            html.append("</small>");
        }

        html.append("</h2>");        
        html.append("</div>");
        html.append("<div class=\"block-content\">");
        
        try {
            pageContext.getOut().write(html.toString());
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }

        return EVAL_BODY_INCLUDE;
    }
    
    /**
     * Renderiza o final da tag.
     * 
     * @see TagSupport#doEndTag()
     */
    @Override
    public int doEndTag() throws JspException {
        StringBuilder html = new StringBuilder();
        html.append("</div>");
        html.append("<div class=\"bendl\"></div>");
        html.append("<div class=\"bendr\"></div>");
        html.append("</div>");
        
        try {
            pageContext.getOut().write(html.toString());
        } catch (IOException ex) {
            LOG.error(ex.getMessage(), ex);
        }
        
        return EVAL_PAGE;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }
}
