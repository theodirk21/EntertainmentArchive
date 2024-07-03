package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;


public class CustomCredentials implements Authentication {


    private final IdentificacaoUsuario identificacaoUsuario;

    public CustomCredentials(IdentificacaoUsuario identificacaoUsuario) {
        if (identificacaoUsuario == null){
            throw new ExceptionInInitializerError("Não é possivel criar um custom authentication, precisa da identificação do usuário");
        }
        this.identificacaoUsuario = identificacaoUsuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.identificacaoUsuario
                .getGrupos()
                .stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao))
                .toList();
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.identificacaoUsuario;
    }

    @Override
    public boolean isAuthenticated() {
        return true;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        throw new IllegalArgumentException("Ja autenticado");
    }

    @Override
    public String getName() {
        return this.identificacaoUsuario.getNome();
    }
}
