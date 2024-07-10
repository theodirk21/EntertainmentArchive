package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.SerieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SerieController.class)
@ExtendWith(MockitoExtension.class)
class SerieControllerTest {

    @MockBean
    private SerieService serieService;

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
    void createSerie() throws Exception {
        var serieRequest = TestUtils.serieRequestDTO();
        var serie = TestUtils.serie();

        when(serieService.create(serieRequest)).thenReturn(serie);

        mockMvc.perform(post("/api/entretenimento/series")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(serieRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(serie.getId()))
                .andExpect(jsonPath("$.nome").value(serie.getNome()));
    }

    @Test
    @WithMockUser
    void getSeries() throws Exception {
        var series = TestUtils.listSeries();

        when(serieService.getSeries()).thenReturn(series);

        mockMvc.perform(get("/api/entretenimento/series")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(series.get(0).getId()))
                .andExpect(jsonPath("$[0].nome").value(series.get(0).getNome()))
                .andExpect(jsonPath("$[1].id").value(series.get(1).getId()))
                .andExpect(jsonPath("$[1].nome").value(series.get(1).getNome()));


    }

    @Test
    void getSerieById() throws Exception {
        var serie =  TestUtils.serie();

        when(serieService.getById(any())).thenReturn(serie);

        mockMvc.perform(get("/api/entretenimento/series/8")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(serie.getId()))
                .andExpect(jsonPath("$.nome").value(serie.getNome()));

    }

    @Test
    void updateSerie() throws Exception {
        var serieRequestUpdate = TestUtils.serieRequestDTOUpdate();

        doNothing().when(serieService).update(anyInt(), eq(serieRequestUpdate));

        mockMvc.perform(put("/api/entretenimento/series/1/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(serieRequestUpdate)))
                .andExpect(status().isNoContent());
    }
}