package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.GrupoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GrupoService {

    @Autowired
    private final GrupoRepository grupoRepository;


    @Transactional
    public Grupo salvar(Grupo grupo) {
        try{
            grupoRepository.save(grupo);
        } catch (RuntimeException e){
            throw new RuntimeException(e);
        }
        return grupo;
    }

    public List<Grupo> listaGrupos() {
        return grupoRepository.findAll();
    }
}
