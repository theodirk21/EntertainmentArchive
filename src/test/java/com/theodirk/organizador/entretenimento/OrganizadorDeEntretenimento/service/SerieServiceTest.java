package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.SerieRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.SerieRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SerieServiceTest {

    @InjectMocks
    private SerieService serieService;

    @Mock
    private SerieRepository serieRepository;

    @Mock
    private ModelMapper mapper;

    @Test
    void create() {
        var serieResquestDTO = TestUtils.serieRequestDTO();
        var serie = TestUtils.serie();

        when(serieRepository.save(any(Serie.class))).thenReturn(serie);
        when(mapper.map(any(SerieRequestDTO.class), eq(Serie.class))).thenReturn(serie);

        var createdSerie = serieService.create(serieResquestDTO);

        verify(serieRepository, times(1)).save(createdSerie);


        assertNotNull(createdSerie);
        assertEquals(TipoEntretenimento.SERIE, createdSerie.getTipo());
        assertNotNull(createdSerie.getDataDeInclusão());
        assertEquals(serieResquestDTO.getNome(), createdSerie.getNome());
        assertEquals(serie, createdSerie);
    }

    @Test
    void getSeries() {
        var series = TestUtils.listSeries();
        when(serieRepository.findAll()).thenReturn(series);

        var listaDeSeries = serieService.getSeries();

        verify(serieRepository, times(1)).findAll();
        assertNotNull(listaDeSeries);
        assertEquals(2, listaDeSeries.size());
    }

    @Test
    void update() {
        var serieNotUpdated =  TestUtils.serie();
        var requestUpdate = TestUtils.serieRequestDTOUpdate();

        when(serieRepository.findById(any())).thenReturn(Optional.ofNullable(serieNotUpdated));
        serieService.update(6, requestUpdate);

        verify(serieRepository).findById(6);
        assertEquals("Serie_update", serieNotUpdated.getNome());
        assertEquals(2003, serieNotUpdated.getAnoLancamento());
        verify(serieRepository).save(serieNotUpdated);
    }

    @Test
    void getById() {
        var serie = TestUtils.serie();

        when(serieRepository.findById(any())).thenReturn(Optional.ofNullable(serie));

        var serieEncontrada = serieService.getById(8);
        verify(serieRepository, times(1)).findById(8);

        assertNotNull(serieEncontrada);
        assertEquals(TipoEntretenimento.SERIE, serieEncontrada.getTipo());
        assertEquals(serie.getId(), serieEncontrada.getId());
        assertEquals(serie.getNumeroTemporadas(), serieEncontrada.getNumeroTemporadas());
        assertEquals(serie.getNumeroEpisodios(), serieEncontrada.getNumeroEpisodios());
        assertEquals(serie.getAnoLancamento(), serieEncontrada.getAnoLancamento());
        assertEquals(serie.getDataDeInclusão(), serieEncontrada.getDataDeInclusão());
        assertEquals(serie.getNome(), serieEncontrada.getNome());
    }
}