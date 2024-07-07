package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.ErroResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.request.FilmeRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.FilmeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/entretenimento/filmes")
@Tag(name = "Filmes ", description = "Retorno de informações de Filmes")
public class FilmeController {

    @Autowired
    private FilmeService filmeService;

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('ADM')")
    @Operation(summary = "Criar filme na base de dados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Filme criado",  content = @Content( mediaType = APPLICATION_JSON_VALUE,
            schema = @Schema(implementation = Filme.class ))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<Object> createFilme(@Valid @RequestBody FilmeRequestDTO filmeRequestDTO){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filmeService.create(filmeRequestDTO));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar filmes criados")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Lista de Filmes"),
            @ApiResponse(responseCode = "404", description = "Filmes não encontrados", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<List<Filme>> getFilmesByName(){
        return ResponseEntity.ok(filmeService.getFilmes());
    }

    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Encontrar filme pelo ID")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Filme requisitado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<Filme> getFilmeById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(filmeService.getById(id));
    }



    @PutMapping(value = "/{id}/update", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualizar filme pelo id")
    @PreAuthorize("hasRole('ADM')")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "204", description = "Filme atualizado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Filme não encontrado", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<Void> updateFilme(@PathVariable("id") Integer id,@Valid @RequestBody FilmeRequestDTO filmeRequest){

        filmeService.updateFilme(id, filmeRequest);
        return ResponseEntity.noContent().build();
    }
}
