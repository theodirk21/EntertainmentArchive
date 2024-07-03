package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class FilmeRequestDTO {

    private String nome;

    private Integer anoLancamento;

    private String diretor;
}
