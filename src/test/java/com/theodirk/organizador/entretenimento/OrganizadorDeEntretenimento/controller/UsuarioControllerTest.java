package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception.BadRequestException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UsuarioController.class)
@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private WebApplicationContext context;


    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
    }


    @Test
    @WithMockUser(roles = "ADM")
    void createUser() throws Exception {
       var usuarioRequest = TestUtils.usuarioRequestDTO();
       var usuarioResponse = TestUtils.usuarioResponseDTO();

        when(usuarioService.create(usuarioRequest.getUsuario(), usuarioRequest.getGrupos()))
                .thenReturn(usuarioResponse);

        mockMvc.perform(post("/api/usuarios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(usuarioRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome").value(usuarioResponse.getNome()))
                .andExpect(jsonPath("$.login").value(usuarioResponse.getLogin()))
                .andExpect(jsonPath("$.grupos").value(usuarioResponse.getGrupos().get(0)));
    }

    @Test
    @WithMockUser(roles = "ADM")
    void getUserByLogin() throws Exception {
        var usuario = TestUtils.usuario();
        var usuarioResponse = TestUtils.usuarioResponseDTO();

        when(usuarioService.obterUsuarioEGrupo(anyString())).thenReturn(usuario);
        when(modelMapper.map(usuario, UsuarioResponseDTO.class)).thenReturn(usuarioResponse);


        mockMvc.perform(get("/api/usuarios/loginTeste")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.login").value(usuarioResponse.getLogin()))
                .andExpect(jsonPath("$.nome").value(usuarioResponse.getNome()))
                .andExpect(jsonPath("$.grupos").value(usuarioResponse.getGrupos().get(0)));

    }

    @Test
    @WithMockUser(roles = "ADM")
    void getListUsuarios() throws Exception {
        var listaResponseUsuario = TestUtils.usuarioResponseDTOList();

        when(usuarioService.listaUsuarios()).thenReturn(listaResponseUsuario);

        mockMvc.perform(get("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].login").value(listaResponseUsuario.get(0).getLogin()))
                .andExpect(jsonPath("$[0].nome").value(listaResponseUsuario.get(0).getNome()))
                .andExpect(jsonPath("$[1].login").value(listaResponseUsuario.get(1).getLogin()))
                .andExpect(jsonPath("$[1].nome").value(listaResponseUsuario.get(1).getNome()));

    }


    @Test
    @WithMockUser(roles = "ADM")
    void handleBadRequestException() throws Exception {
        when(usuarioService.obterUsuarioEGrupo(anyString())).thenThrow(new BadRequestException(Collections.singletonList("Bad Request")));

        mockMvc.perform(get("/api/usuarios/usuarioInexistente")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.description").value("Bad Request"));
    }
}