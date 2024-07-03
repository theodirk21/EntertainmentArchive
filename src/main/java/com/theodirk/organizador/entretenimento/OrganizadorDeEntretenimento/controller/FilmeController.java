package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.ErroResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.FilmeRequestDTO;
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

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createFilme(@Valid @RequestBody FilmeRequestDTO filmeRequestDTO){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(filmeService.create(filmeRequestDTO));
    }

    @GetMapping()
    public ResponseEntity<List<Filme>> getFilmesByName(){
        return ResponseEntity.ok(filmeService.getFilmes());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Filme> getFilmeById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(filmeService.getById(id));
    }



    @PutMapping(value = "/{id}/update", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update filme by id")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses( value = {
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
        return ResponseEntity.ok().build();
    }
}
