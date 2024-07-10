package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@ExtendWith(MockitoExtension.class)
public class CustomCredentialsTest {

    @InjectMocks
    private CustomCredentials customCredentials;

    @Mock
    private IdentificacaoUsuario identificacaoUsuario;

    @Test
    public void testCustomCredentialsInitialization() {

        when(identificacaoUsuario.getNome()).thenReturn("testUser");


        assertEquals("testUser", customCredentials.getName());
        assertEquals(identificacaoUsuario, customCredentials.getPrincipal());
        assertNull(customCredentials.getCredentials());
        assertNull(customCredentials.getDetails());
        assertTrue(customCredentials.isAuthenticated());
    }

    @Test
    public void testGetAuthorities() {
        IdentificacaoUsuario identificacaoUsuario = mock(IdentificacaoUsuario.class);
        when(identificacaoUsuario.getGrupos()).thenReturn(List.of("ROLE_USER", "ROLE_ADMIN"));

        CustomCredentials customCredentials = new CustomCredentials(identificacaoUsuario);

        Collection<? extends GrantedAuthority> authorities = customCredentials.getAuthorities();
        List<String> authorityNames = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        assertEquals(2, authorities.size());
        assertTrue(authorityNames.contains("ROLE_USER"));
        assertTrue(authorityNames.contains("ROLE_ADMIN"));
    }
}
