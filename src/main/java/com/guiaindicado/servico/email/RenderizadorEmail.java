package com.guiaindicado.servico.email;

import java.io.StringWriter;
import java.util.Map;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.guiaindicado.dominio.email.ModeloEmail;

@Component
public class RenderizadorEmail {

    @Autowired
    private VelocityEngine velocityEngine;

    public String renderizar(Map<String, Object> contexto, ModeloEmail.Tipo modelo) {
        VelocityContext contextoVelocity = new VelocityContext();

        for (Map.Entry<String, ?> entrada : contexto.entrySet()) {
            contextoVelocity.put(entrada.getKey(), entrada.getValue());
        }

        Template modeloVelocity = velocityEngine.getTemplate(modelo.getNome(), "utf-8");
        StringWriter emailRenderizado = new StringWriter();
        modeloVelocity.merge(contextoVelocity, emailRenderizado);

        return emailRenderizado.toString();
    }
}
