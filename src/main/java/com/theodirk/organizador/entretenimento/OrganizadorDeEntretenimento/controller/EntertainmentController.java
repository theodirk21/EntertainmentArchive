package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.FilmeService;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.SerieService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/entretenimento")
@Tag(name = "Entretenimento ", description = "Retorno de informações de Filmes e Séries")
public class EntertainmentController {

    @Autowired
    private FilmeService filmeService;


    @Autowired
    private SerieService serieService;



    @GetMapping
    public ResponseEntity<List<Object>> getEntertainment(){
        List<Object> entretenimento = Arrays.stream(new Object[][] {
                        {filmeService.getFilmes()},
                        {serieService.getSeries()}
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(entretenimento);
    }
}
