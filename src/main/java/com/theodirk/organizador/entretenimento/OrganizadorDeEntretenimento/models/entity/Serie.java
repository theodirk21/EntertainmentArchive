package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity;


import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.TipoEntretenimento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "serie")
@Builder
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 500)
    private String nome;

    @Column(name = "ano_lancamento")
    private Integer anoLancamento;

    @Column(name = "nu_temporadas")
    private Integer numeroTemporadas;

    @Column(name = "nu_episodios")
    private Integer numeroEpisodios;

    @Column(name = "dt_inclusao")
    private LocalDateTime dataDeInclusão;

    @Column(name = "tipo_entretenimento")
    @Enumerated(EnumType.STRING)
    private TipoEntretenimento tipo;



}
