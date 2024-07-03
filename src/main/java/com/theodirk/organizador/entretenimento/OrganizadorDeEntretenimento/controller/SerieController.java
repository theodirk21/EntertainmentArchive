package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.ErroResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.SerieRequestDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.SerieService;
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
@RequestMapping("/api/entretenimento/series")
@Tag(name = "Séries ", description = "Retorno de informações de Séries")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> createSerie(@Valid @RequestBody SerieRequestDTO serieRequestDTO){

                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(serieService.create(serieRequestDTO));
    }


    @GetMapping()
    public ResponseEntity<List<Serie>> getSeries(){
        return ResponseEntity.ok(serieService.getSeries());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Serie> getSerieById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(serieService.getById(id));
    }


    @PutMapping(value = "/{id}/update", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Update serie by nome")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou ausentes no corpo da requisição", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "404", description = "Serie não encontrada", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<Void> updateSerie(@PathVariable("id") Integer id, @RequestBody SerieRequestDTO serieRequestDTO){

        serieService.update(id, serieRequestDTO);
        return ResponseEntity.ok().build();
    }
}
