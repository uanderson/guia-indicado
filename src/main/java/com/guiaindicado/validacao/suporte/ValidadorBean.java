package com.guiaindicado.validacao.suporte;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Path.Node;
import javax.validation.Validation;
import javax.validation.Validator;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import com.guiaindicado.notificacao.Notificador;

/**
 * Realiza as validações com objetos que usam bean validation.
 * 
 * @author Uanderson Soares Gonçalves
 */
public final class ValidadorBean {

    /**
     * Valida um objeto e adiciona as violações ao Notificador. 
     * 
     * @param bean Objeto a ser validado
     * @return true caso seja válido, falso caso contrário.
     */
    public static <T> boolean estaValido(T bean) {
        Preconditions.checkNotNull(bean);
        
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> erros = validator.validate(bean);

        for (ConstraintViolation<T> erro : erros) {
            Node no = Iterators.get(erro.getPropertyPath().iterator(), 0);
            Notificador.erro(no.getName(), erro.getMessage());
        }

        return erros.isEmpty();
    }
    
    private ValidadorBean() {
    }
}
