package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception.NotFoundException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.FilmeRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.FilmeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;


import java.rmi.NotBoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class FilmeServiceTest {

    @InjectMocks
    private FilmeService filmeService;

    @Mock
    private FilmeRepository filmeRepository;

    @Mock
    private ModelMapper mapper;


    @Test
    @DisplayName("Teste para criação de Filmes")
    void createFilme() {
        var filmeRequestDTO = TestUtils.filmeRequestDTO();
        var filme = TestUtils.filme();

        when(filmeRepository.save(any(Filme.class))).thenReturn(filme);
        when(mapper.map(any(FilmeRequestDTO.class), eq(Filme.class))).thenReturn(filme);

        var createdFilme = filmeService.create(filmeRequestDTO);

        verify(filmeRepository, times(1)).save(createdFilme);

        assertNotNull(createdFilme);
        assertEquals(TipoEntretenimento.FILME, createdFilme.getTipo());
        assertNotNull(createdFilme.getDataDeInclusão());
        assertEquals(filmeRequestDTO.getNome(), createdFilme.getNome());

        assertEquals(filme, createdFilme);
    }

    @Test
    @DisplayName("Teste para obtenção de todos os filmes armazenados")
    void getFilmes() {
        var filmes = TestUtils.listFilmes();
        when(filmeRepository.findAll()).thenReturn(filmes);

        var listaDeFilmes = filmeService.getFilmes();
        verify(filmeRepository, times(1)).findAll();


        assertNotNull(listaDeFilmes);
        assertEquals(2, listaDeFilmes.size());
    }


    @Test
    @DisplayName("Teste para atualização de informações de filmes")
    void updateFilme() {

        var filmeNotUpdated = TestUtils.filme();
        var requestUpdate = TestUtils.filmeRequestDTOForUpdate();

        when(filmeRepository.findById(any())).thenReturn(Optional.ofNullable(filmeNotUpdated));
        filmeService.updateFilme(1, requestUpdate);

        verify(filmeRepository).findById(1);
        assertEquals("Filme_update", filmeNotUpdated.getNome());
        assertEquals(2020, filmeNotUpdated.getAnoLancamento());
        verify(filmeRepository).save(filmeNotUpdated);



    }

    @Test
    @DisplayName("Teste para encontrar um filme pelo id")
    void getById() {
        var filme = TestUtils.filme();

        when(filmeRepository.findById(any())).thenReturn(Optional.ofNullable(filme));

        var filmeEncontrado = filmeService.getById(3);
        verify(filmeRepository,times(1)).findById(3);

        assertNotNull(filmeEncontrado);
        assertEquals(TipoEntretenimento.FILME, filmeEncontrado.getTipo());
        assertEquals(filme.getId(), filmeEncontrado.getId());
        assertEquals(filme.getDiretor(), filmeEncontrado.getDiretor());
        assertEquals(filme.getAnoLancamento(), filmeEncontrado.getAnoLancamento());
        assertEquals(filme.getDataDeInclusão(), filmeEncontrado.getDataDeInclusão());
        assertEquals(filme.getNome(), filmeEncontrado.getNome());
    }

    @Test
    @DisplayName("Teste para filme não encontrado pelo id")
    void getById_NotFound() {

        when(filmeRepository.findById(any())).thenThrow(new NotFoundException("Filme não encontrado"));

       assertThrows(NotFoundException.class, () -> filmeService.getById(3));
    }
}