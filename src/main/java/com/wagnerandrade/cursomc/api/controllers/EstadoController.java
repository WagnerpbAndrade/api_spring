package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.dto.EstadoDTO;
import com.wagnerandrade.cursomc.api.services.CidadeService;
import com.wagnerandrade.cursomc.api.services.EstadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/estados")
public class EstadoController {

    @Autowired
    private EstadoService service;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> getAll(){
        return ResponseEntity.ok().body(this.service.getALL());
    }

    @GetMapping(value = "/{estadoId}/cidades")
    public ResponseEntity findCidades(@PathVariable(name = "estadoId") Long id) {
        return ResponseEntity.ok().body(this.cidadeService.findByEstado(id));
    }

}
