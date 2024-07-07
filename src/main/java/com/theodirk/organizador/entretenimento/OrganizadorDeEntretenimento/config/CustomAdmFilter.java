package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.config;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.CustomCredentials;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.IdentificacaoUsuario;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class CustomAdmFilter extends OncePerRequestFilter {
        @Override
        protected void doFilterInternal(
                HttpServletRequest request,
                HttpServletResponse response,
                FilterChain filterChain) throws ServletException, IOException {

            String secretHeader = request.getHeader("id-secret");

            if(secretHeader != null){
                if(secretHeader.equals("secr3t-key")){
                    var identificacaoUsuario = new IdentificacaoUsuario(
                            "id-secret",
                            "Adm secret",
                            "id-secret",
                            List.of("ADM")
                    );
                    Authentication authentication = new CustomCredentials(identificacaoUsuario);

                    SecurityContext securityContext = SecurityContextHolder.getContext();
                    securityContext.setAuthentication(authentication);
                }
            }

            filterChain.doFilter(request, response);
        }
    }
