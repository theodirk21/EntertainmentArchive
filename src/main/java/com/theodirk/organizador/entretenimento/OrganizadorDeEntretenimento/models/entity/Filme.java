package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity;


import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "filme")
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 500)
    private String nome;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "diretor", length = 50)
    private String diretor;

    @Column(name = "dt_inclusao")
    private LocalDateTime dataDeInclusão;

    @Column(name = "tipo_entretenimento")
    @Enumerated(EnumType.STRING)
    private TipoEntretenimento tipo;

}
