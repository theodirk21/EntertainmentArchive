package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.config.SecurityConfig;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.FilmeRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.FilmeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Base64;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.when;


@WebMvcTest(FilmeController.class)
@ExtendWith(MockitoExtension.class)
class FilmeControllerTest {

    @MockBean
    private FilmeService filmeService;

    @Autowired
    private MockMvc mockMvc;

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
    void createFilme() throws Exception {
        var filmeRequest = TestUtils.filmeRequestDTO();
        var filme = TestUtils.filme();

        when(filmeService.create(filmeRequest)).thenReturn(filme);

        mockMvc.perform(post("/api/entretenimento/filmes")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(filmeRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(filme.getId()))
                .andExpect(jsonPath("$.nome").value(filme.getNome()));
    }

    @Test
    @WithMockUser
    void getFilmeById() throws Exception {
        var filme = TestUtils.filme();

        when(filmeService.getById(any())).thenReturn(filme);

        mockMvc.perform(get("/api/entretenimento/filmes/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(filme.getId()))
                .andExpect(jsonPath("$.nome").value(filme.getNome()));
    }

    @Test
    @WithMockUser(roles = "ADM")
    void updateFilme() throws Exception {
        var filmeRequestUpdate = TestUtils.filmeRequestDTOForUpdate();

        doNothing().when(filmeService).updateFilme(anyInt(), eq(filmeRequestUpdate));

        mockMvc.perform(put("/api/entretenimento/filmes/1/update")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(mapper.writeValueAsString(filmeRequestUpdate)))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser
    void getListaDeFilmes() throws Exception {
    var listaFilmes = TestUtils.listFilmes();

    when(filmeService.getFilmes()).thenReturn(listaFilmes);

    mockMvc.perform(get("/api/entretenimento/filmes")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").value(listaFilmes.get(0).getId()))
            .andExpect(jsonPath("$[0].nome").value(listaFilmes.get(0).getNome()))
            .andExpect(jsonPath("$[1].id").value(listaFilmes.get(1).getId()))
            .andExpect(jsonPath("$[1].nome").value(listaFilmes.get(1).getNome()));

    }
}