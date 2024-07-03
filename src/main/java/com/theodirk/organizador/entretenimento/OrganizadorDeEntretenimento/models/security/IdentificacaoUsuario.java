package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.security;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdentificacaoUsuario {
    private String id;
    private String nome;
    private String login;
    private List<String> grupos;

    public List<String> getGrupos() {
        if (grupos == null){
            grupos = new ArrayList<>();
        }
        return grupos;
    }
}
