package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class FilmeRequestDTO {

    private String nome;

    private Integer anoLancamento;

    private String diretor;
}
