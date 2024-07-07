package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FilmeESerieResponseDTO {
    private List<Filme> filme;
    private List<Serie> serie;
}
