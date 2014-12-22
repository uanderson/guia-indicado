package com.guiaindicado.ui.formatacao;

import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import com.google.common.collect.Sets;

public class FabricaFormatoTelefone implements AnnotationFormatterFactory<FormatoTelefone> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> tiposCampo = Sets.newHashSet();
        tiposCampo.add(String.class);
        return tiposCampo;
    }

    @Override
    public Parser<String> getParser(FormatoTelefone anotacao, Class<?> tipoCampo) {
        return new FormatadorTelefone();
    }
    
    @Override
    public Printer<String> getPrinter(FormatoTelefone anotacao, Class<?> tipoCampo) {
        return new FormatadorTelefone();
    }

}
