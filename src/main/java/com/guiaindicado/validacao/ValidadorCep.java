package com.guiaindicado.validacao;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidadorCep implements ConstraintValidator<Cep, String> {
    
    public void initialize(Cep constraintAnnotation) {
    }

    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null || valor.isEmpty()) {
            return true;
        }
        
        int tamanho = valor.length();
        return tamanho == 8;
    }
}
