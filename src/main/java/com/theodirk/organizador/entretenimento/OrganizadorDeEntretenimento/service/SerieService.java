package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.exception.NotFoundException;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.SerieRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository.SerieRepository;

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
public class SerieService {

    @Autowired
    private final SerieRepository serieRepository;

    private final ModelMapper mapper;


    @Transactional
    public Serie create(SerieRequestDTO serieRequestDTO) {

        var serie = mapper.map(serieRequestDTO, Serie.class);
        serie.setTipo(TipoEntretenimento.SERIE);
        serie.setDataDeInclusão(LocalDateTime.now());
        return serieRepository.save(serie);
    }

    public List<Serie> getSeries(){

        return serieRepository.findAll();
    }

    @Transactional
    public void update(Integer id, SerieRequestDTO serieRequestDTO){

        var serie = getById(id);
        Optional.ofNullable(serieRequestDTO.getNome()).ifPresent(serie::setNome);
        Optional.ofNullable(serieRequestDTO.getAnoLancamento()).ifPresent(serie::setAnoLancamento);
        Optional.ofNullable(serieRequestDTO.getNumeroTemporadas()).ifPresent(serie::setNumeroTemporadas);
        Optional.ofNullable(serieRequestDTO.getNumeroEpisodios()).ifPresent(serie::setNumeroEpisodios);
        serieRepository.save(serie);
    }

    public Serie getById(Integer id) {

        return serieRepository.findById(id).orElseThrow(() -> new NotFoundException("Série não encontrada"));
    }
}
