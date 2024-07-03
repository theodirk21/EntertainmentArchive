package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository;


import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByLogin(String login);
}

