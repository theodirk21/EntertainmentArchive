package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SerieRepository extends JpaRepository<Serie, Integer> {

}
