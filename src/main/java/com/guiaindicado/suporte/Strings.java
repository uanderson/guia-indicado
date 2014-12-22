package com.guiaindicado.suporte;

import java.text.Normalizer;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;

/**
 * Métodos utilitários para o tratamento de String
 * 
 * @author Uanderson Soares Gonçalves
 */
public final class Strings {

    /**
     * Remove todos os acentos e caracteres especiais mantendo apenas letras números e espaços.
     * 
     * @param origem String de origem
     * @return String normalizada
     */
    public static String removerAcentoEspecial(String origem) {
        if (origem == null) {
            return "";
        }
        
        String resultado = origem.replaceAll("-", " ");
        resultado = Joiner.on(" ").join(Splitter.on(" ").omitEmptyStrings().split(resultado));
        resultado = Normalizer.normalize(resultado.trim(), Normalizer.Form.NFD);
        resultado = resultado.replaceAll("[^\\p{ASCII}]", "")
            .replaceAll("[^\\w\\d\\s]", "");
        
        return resultado;
    }
    
    /**
     * Substitui os espaços por hifens.
     * 
     * @param origem String origem
     * @return String hifenizada
     */
    public static String hifenizar(String origem) {
       return hifenizar(origem, false);
    }
    
    /**
     * Substitui os espaços por hifens, porém, normaliza ou não a string baseado no
     * parâmetro.
     * 
     * @param origem String origem
     * @param normalizar Indicador de normalização
     * @return
     */
    public static String hifenizar(String origem, boolean normalizar) {
        if (origem == null) {
            return null;
        }
        
        String retorno = origem;
        
        if (normalizar) {
            retorno = removerAcentoEspecial(origem);
        }
        
        return Joiner.on("-").join(Splitter.on(" ").split(retorno));
    }
    
    private Strings() {
    }
}
