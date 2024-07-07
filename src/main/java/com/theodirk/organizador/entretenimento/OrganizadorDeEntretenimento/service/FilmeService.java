package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception.NotFoundException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.FilmeRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.FilmeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilmeService {

    @Autowired
    private final FilmeRepository filmeRepository;

    private final ModelMapper mapper;

    public List<Filme> getFilmes(){
        return filmeRepository.findAll();
    }

    @Transactional
    public Filme create(FilmeRequestDTO filmeRequest){

       var filme = mapper.map(filmeRequest, Filme.class);
       filme.setTipo(TipoEntretenimento.FILME);
       filme.setDataDeInclusão(LocalDateTime.now());
       return filmeRepository.save(filme);
    }

    @Transactional
    public void updateFilme(Integer id, FilmeRequestDTO filmeRequestDTO){

        var filme = getById(id);
        Optional.ofNullable(filmeRequestDTO.getNome()).ifPresent(filme::setNome);
        Optional.ofNullable(filmeRequestDTO.getAnoLancamento()).ifPresent(filme::setAnoLancamento);
        Optional.ofNullable(filmeRequestDTO.getDiretor()).ifPresent(filme::setDiretor);
        filmeRepository.save(filme);
    }

    public Filme getById(Integer id) {
        return filmeRepository.findById(id).orElseThrow(() -> new NotFoundException("Filme não encontrado"));
    }
}
