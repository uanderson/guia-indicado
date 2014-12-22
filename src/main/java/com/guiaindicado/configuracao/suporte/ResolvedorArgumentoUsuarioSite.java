package com.guiaindicado.configuracao.suporte;

import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.guiaindicado.seguranca.UsuarioAnonimo;
import com.guiaindicado.seguranca.UsuarioSite;

public class ResolvedorArgumentoUsuarioSite implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return UsuarioSite.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Authentication autenticacao = (Authentication) webRequest.getUserPrincipal();

        if (autenticacao != null && autenticacao.getPrincipal() instanceof UsuarioSite) {
            return autenticacao.getPrincipal();
        } else {
            return UsuarioAnonimo.criar();
        }
    }
}
