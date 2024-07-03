package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.repository;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.UsuarioGrupo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioGrupoRepository extends JpaRepository<UsuarioGrupo, String> {


    @Query("""
            select distinct g.nome
            from UsuarioGrupo ug
            join ug.grupo g
            join ug.usuario u
            where u = ?1   
            
    """)
    List<String> findGrupoByUsuario(Usuario usuario);
}
