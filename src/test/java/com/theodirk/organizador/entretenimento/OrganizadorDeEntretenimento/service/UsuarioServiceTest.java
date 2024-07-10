package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.GrupoRepository;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.UsuarioGrupoRepository;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private  UsuarioRepository usuarioRepository;

    @Mock
    private  GrupoRepository grupoRepository;

    @Mock
    private  UsuarioGrupoRepository usuarioGrupoRepository;

    @Mock
    private  PasswordEncoder passwordEncoder;

    @Mock
    private  ModelMapper mapper;




    @Test
    void create() {
        var usuario = TestUtils.usuario();
        var grupo = List.of("USER");
        var usuarioResponse = TestUtils.usuarioResponseDTO1();
        usuarioResponse.setGrupos(grupo);
        when(grupoRepository.findByNome(anyString())).thenReturn(Optional.ofNullable(TestUtils.grupo()));
        when(mapper.map(eq(usuario), eq(UsuarioResponseDTO.class))).thenReturn(usuarioResponse);

        var response = usuarioService.create(usuario, grupo);

        assertNotNull(response);
        assertEquals(usuario.getNome(), response.getNome());
        assertEquals(grupo, response.getGrupos());
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        verify(grupoRepository, times(1)).findByNome(any(String.class));
    }

    @Test
    void obterUsuarioEGrupo() {
        var usuario = TestUtils.usuario();
        var grupo = List.of("USER");

        when(usuarioRepository.findByLogin(any())).thenReturn(Optional.of(usuario));
        when(usuarioGrupoRepository.findGrupoByUsuario(usuario)).thenReturn(grupo);

        var resultado = usuarioService.obterUsuarioEGrupo("usuarioLogin");


        assertNotNull(resultado);
        assertEquals(usuario.getLogin(), resultado.getLogin());
        assertEquals(grupo, resultado.getGrupos());
        verify(usuarioRepository, times(1)).findByLogin(usuario.getLogin());
        verify(usuarioGrupoRepository, times(1)).findGrupoByUsuario(usuario);
    }

    @Test
    void listaUsuarios() {
        var usuarioList = TestUtils.listaUsuario();
        var grupo1 = TestUtils.grupoString();
        var grupo2 = TestUtils.listGroupoString();
        var usuarioResponseDTO1 = TestUtils.usuarioResponseDTO1();
        var usuarioResponseDTO2 = TestUtils.usuarioResponseDTO2();

        when(usuarioRepository.findAll()).thenReturn(usuarioList);
        when(usuarioGrupoRepository.findGrupoByUsuario(usuarioList.get(0))).thenReturn(grupo1);
        when(usuarioGrupoRepository.findGrupoByUsuario(usuarioList.get(1))).thenReturn(grupo2);
        when(mapper.map(usuarioList.get(0), UsuarioResponseDTO.class)).thenReturn(usuarioResponseDTO1);
        when(mapper.map(usuarioList.get(1), UsuarioResponseDTO.class)).thenReturn(usuarioResponseDTO2);

        var resultado = usuarioService.listaUsuarios();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());

        verify(usuarioRepository, times(1)).findAll();
        verify(usuarioGrupoRepository, times(1)).findGrupoByUsuario(usuarioList.get(0));
        verify(usuarioGrupoRepository, times(1)).findGrupoByUsuario(usuarioList.get(1));

        verify(mapper, times(1)).map(usuarioList.get(0), UsuarioResponseDTO.class);
        verify(mapper, times(1)).map(usuarioList.get(1), UsuarioResponseDTO.class);

        assertEquals(grupo1, usuarioResponseDTO1.getGrupos());
        assertEquals(grupo2, usuarioResponseDTO2.getGrupos());

    }
}