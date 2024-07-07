package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class SerieRequestDTO {

    private String nome;

    private Integer anoLancamento;

    private Integer numeroTemporadas;

    private Integer numeroEpisodios;
}
