package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.ErroResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.dto.response.FilmeESerieResponseDTO;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Filme;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Serie;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.FilmeService;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.SerieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@RestController
@RequestMapping("/api/entretenimento")
@Tag(name = "Entretenimento ", description = "Retorno de informações de Filmes e Séries")
public class EntertainmentController {

    @Autowired
    private FilmeService filmeService;


    @Autowired
    private SerieService serieService;

    public static final List<Class<?>> CLASSES = List.of(Filme.class, Serie.class);


    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Listar filmes e séries")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Lista de filmes e séries"),
            @ApiResponse(responseCode = "404", description = "Nenhuma informação encontrada", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            )),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(
                    mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErroResponseDTO.class)
            ))
    })
    public ResponseEntity<FilmeESerieResponseDTO> getEntertainment(){
        FilmeESerieResponseDTO entretenimento = new FilmeESerieResponseDTO(
                filmeService.getFilmes(),
                serieService.getSeries());

        return ResponseEntity.ok(entretenimento);
    }
}
