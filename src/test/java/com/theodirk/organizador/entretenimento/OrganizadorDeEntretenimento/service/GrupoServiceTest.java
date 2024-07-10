package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.GrupoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.TestUtils;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GrupoServiceTest {

    @InjectMocks
    private GrupoService grupoService;

    @Mock
    private GrupoRepository grupoRepository;


    @Test
    void criarGrupo() {
        var grupo = TestUtils.grupo();

        when(grupoRepository.save(any(Grupo.class))).thenReturn(grupo);

        var createdGrupo = grupoService.salvar(grupo);
        verify(grupoRepository, times(1)).save(createdGrupo);

        assertNotNull(createdGrupo);
        assertEquals(grupo.getNome(), createdGrupo.getNome());
        assertEquals(grupo, createdGrupo);
    }

    @Test
    void listaGrupos() {
        var grupos = TestUtils.listGroupo();

        when(grupoRepository.findAll()).thenReturn(grupos);
        var listaDeGrupos = grupoService.listaGrupos();
        verify(grupoRepository, times(1)).findAll();

        assertNotNull(listaDeGrupos);
        assertEquals(2, listaDeGrupos.size());
    }
}