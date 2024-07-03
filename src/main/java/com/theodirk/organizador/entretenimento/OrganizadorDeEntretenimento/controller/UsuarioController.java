package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;


import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.UsuarioRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Usuario;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.UsuarioService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuário", description = "Controle de usuário")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final ModelMapper mapper;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponseDTO> createUser(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.create(usuarioRequestDTO.getUsuario(), usuarioRequestDTO.getGrupos()));
    }

    @GetMapping("{login}")
    public ResponseEntity<UsuarioResponseDTO> getUserByLogin(@PathVariable("login") String login){
        var usuario = usuarioService.obterUsuarioEGrupo(login);
        UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<List<UsuarioResponseDTO>> getListUsuarios(){
        return ResponseEntity.ok(usuarioService.listaUsuarios());
    }
}
