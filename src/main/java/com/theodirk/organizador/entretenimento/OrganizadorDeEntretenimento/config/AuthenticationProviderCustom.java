package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.config;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.CustomCredentials;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.IdentificacaoUsuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthenticationProviderCustom implements AuthenticationProvider {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String senha = (String) authentication.getCredentials();

        Usuario usuario = usuarioService.obterUsuarioEGrupo(login);
        if (usuario != null) {
            boolean senhaValidada = passwordEncoder.matches(senha, usuario.getSenha());
            if (senhaValidada) {
                IdentificacaoUsuario identificacaoUsuario = new IdentificacaoUsuario().builder()
                        .id(usuario.getId())
                        .nome(usuario.getNome())
                        .login(usuario.getLogin())
                        .grupos(usuario.getGrupos()).build();

            return new CustomCredentials(identificacaoUsuario);
        }
    }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}
