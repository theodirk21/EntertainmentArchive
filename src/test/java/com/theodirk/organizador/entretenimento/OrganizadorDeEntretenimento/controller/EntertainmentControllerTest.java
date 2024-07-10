package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.FilmeService;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.SerieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;


import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.Mockito.when;

@WebMvcTest(EntertainmentController.class)
@ExtendWith(MockitoExtension.class)
class EntertainmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FilmeService filmeService;

    @MockBean
    private SerieService serieService;

    @Test
    @WithMockUser
    void getEntertainment() throws Exception {

        var filme = TestUtils.filme();
        var serie = TestUtils.serie();

        when(filmeService.getFilmes()).thenReturn(List.of(filme));
        when(serieService.getSeries()).thenReturn(List.of(serie));

        mockMvc.perform(get("/api/entretenimento")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filme").isArray())
                .andExpect(jsonPath("$.serie").isArray());
    }

    @Test
    @WithMockUser
    void notGetEntertainment() throws Exception {

        when(filmeService.getFilmes()).thenReturn(List.of());
        when(serieService.getSeries()).thenReturn(List.of());

        mockMvc.perform(get("/api/entretenimento")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filme").isEmpty())
                .andExpect(jsonPath("$.serie").isEmpty());
    }
}