package com.guiaindicado.ui.formatacao;

import java.util.Set;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;

import com.google.common.collect.Sets;

public class FabricaFormatoCep implements AnnotationFormatterFactory<FormatoCep> {

    @Override
    public Set<Class<?>> getFieldTypes() {
        Set<Class<?>> tiposCampo = Sets.newHashSet();
        tiposCampo.add(String.class);
        return tiposCampo;
    }
    
    @Override
    public Parser<String> getParser(FormatoCep anotacao, Class<?> tipoCampo) {
        return new FormatadorCep();
    }
    
    @Override
    public Printer<String> getPrinter(FormatoCep anotacao, Class<?> tipoCampo) {
        return new FormatadorCep();
    }
}
