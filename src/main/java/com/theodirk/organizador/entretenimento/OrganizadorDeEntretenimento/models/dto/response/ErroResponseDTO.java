package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.*;


import java.io.Serializable;
import java.util.List;


@JsonInclude(Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErroResponseDTO implements Serializable {

    private String description;
    private List<String> details;

}
