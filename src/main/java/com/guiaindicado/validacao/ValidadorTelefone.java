package com.guiaindicado.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorTelefone implements ConstraintValidator<Telefone, String> {
    
    public void initialize(Telefone constraintAnnotation) {
    }

    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null || valor.isEmpty()) {
            return true;
        }
        
        int tamanho = valor.length();
        return (tamanho >= 10 && tamanho <= 11);
    }
}
