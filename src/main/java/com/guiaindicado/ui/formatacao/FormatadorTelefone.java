package com.guiaindicado.ui.formatacao;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Parser;
import org.springframework.format.Printer;


public class FormatadorTelefone implements Parser<String>, Printer<String> {

    @Override
    public String parse(String texto, Locale local) throws ParseException {
        return texto.replaceAll("[^0-9]", "");
    }

    @Override
    public String print(String telefone, Locale local) {
        return Funcoes.formatarTelefone(telefone);
    }
}
