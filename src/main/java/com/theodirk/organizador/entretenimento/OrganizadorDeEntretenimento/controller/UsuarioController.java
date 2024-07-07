package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;


import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.ErroResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.UsuarioRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.UsuarioResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "Usuário", description = "Controle de usuário")
@RequiredArgsConstructor
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    private final ModelMapper mapper;


    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Criar usuário no banco de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Usuario criaado",  content = @Content( mediaType = APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UsuarioResponseDTO.class ))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<UsuarioResponseDTO> createUser(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(usuarioService.create(usuarioRequestDTO.getUsuario(), usuarioRequestDTO.getGrupos()));
    }

    @GetMapping(value = "{login}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Encontrar usuário pelo login")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Usuario requisitado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<UsuarioResponseDTO> getUserByLogin(@PathVariable("login") String login){
        var usuario = usuarioService.obterUsuarioEGrupo(login);
        UsuarioResponseDTO response = mapper.map(usuario, UsuarioResponseDTO.class);
        return ResponseEntity.ok(response);
    }

    @GetMapping( produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuários criados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuários"),
            @ApiResponse(responseCode = "404", description = "Usuários não encontrados", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<List<UsuarioResponseDTO>> getListUsuarios(){
        return ResponseEntity.ok(usuarioService.listaUsuarios());
    }
}
