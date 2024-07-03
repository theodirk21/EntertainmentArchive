package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GrupoRepository  extends JpaRepository<Grupo, String> {

    Optional<Grupo> findByNome(String nome);

}
