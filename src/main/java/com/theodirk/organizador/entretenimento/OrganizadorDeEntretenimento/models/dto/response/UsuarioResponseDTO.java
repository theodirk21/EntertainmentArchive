package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private String id;
    private String nome;
    private String login;
    private List<String> grupos;
}
