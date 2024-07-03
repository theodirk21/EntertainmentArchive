package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {
    private String id;
    private String nome;
    private String login;
    private List<String> grupos;
}
