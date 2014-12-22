package com.guiaindicado.ui.formatacao;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Strings;

/**
 * Funções para formatação de dados padrões do guia indicado. Elas são utilizadas em
 * vários locais, mas, principalmente no JSP.
 * 
 * @author Uanderson Soares Gonçalves
 */
public class Funcoes {

    /**
     * Formata o CEP passado.
     * 
     * @param cep CEP a ser formatado
     * @return CEP formatado
     */
    public static String formatarCep(String cep) {
        if (StringUtils.length(cep) == 8) {
            return cep.substring(0, 5) + "-" + cep.substring(5);
        }
        
        return cep;
    }
    
    /**
     * Formata o telefone passado.
     * 
     * @param telefone Telefone a ser formatado
     * @return Telefone formatado
     */
    public static String formatarTelefone(String telefone) {
        if (StringUtils.length(telefone) == 10) {
            return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 6) + "-"
                + telefone.substring(6);
        }
        
        return telefone;
    }
    
    /**
     * Formata a cidade com o estado entre parênteses na frente: Cidade (Estado). Caso o estado
     * seja vazio ou nulo, a formatação mantém apenas a cidade.
     * 
     * @param cidade Cidade a ser formatada
     * @param estado Estado a ser formatado
     * @return Cidade mais o estado formatados
     */
    public static String formatarCidade(String cidade, String estado) {
        if (Strings.isNullOrEmpty(cidade)) {
            return "";
        }
        
        String formatado = cidade;
        
        if (!Strings.isNullOrEmpty(estado)) {
            formatado += " (" + estado + ")";
        }
        
        return formatado;
    }
}
