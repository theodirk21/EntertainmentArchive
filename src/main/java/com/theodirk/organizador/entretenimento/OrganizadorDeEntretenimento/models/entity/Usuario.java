package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "nome", length = 500)
    private String nome;

    @Column(name = "login", length = 500)
    private String login;

    @Column(name= "pass")
    private String senha;

    @Transient
    private List<String> grupos;
}
