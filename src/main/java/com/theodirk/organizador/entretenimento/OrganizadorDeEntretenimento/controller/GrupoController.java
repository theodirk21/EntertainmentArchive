package com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.controller;

import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.models.entity.Grupo;
import com.theodirk.organizador.entretenimento.OrganizadorDeEntretenimento.service.GrupoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles/grupos")
@RequiredArgsConstructor
public class GrupoController {

    private final GrupoService grupoService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Grupo> salvar(@RequestBody Grupo grupo){
       return ResponseEntity.ok(grupoService.salvar(grupo));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Grupo>> listaGrupos(){
        return ResponseEntity.ok(grupoService.listaGrupos());
    }
}
