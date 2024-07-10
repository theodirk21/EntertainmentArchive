package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.CustomCredentials;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security.IdentificacaoUsuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.GrupoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(GrupoController.class)
@ExtendWith(MockitoExtension.class)
class GrupoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GrupoService grupoService;

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
    void criarGrupo() throws Exception {

        var grupo = TestUtils.grupo();

        when(grupoService.salvar(any(Grupo.class))).thenReturn(grupo);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/roles/grupos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(grupo)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(grupo.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value(grupo.getNome()));
    }

    @Test
    @WithMockUser
    void listaGrupos() throws Exception {
        var grupos = TestUtils.listGroupo();

        when(grupoService.listaGrupos()).thenReturn(grupos);

        mockMvc.perform(get("/api/roles/grupos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(grupos.get(0).getId()))
                .andExpect(jsonPath("$[0].nome").value(grupos.get(0).getNome()))
                .andExpect(jsonPath("$[1].id").value(grupos.get(1).getId()))
                .andExpect(jsonPath("$[1].nome").value(grupos.get(1).getNome()));

    }
}