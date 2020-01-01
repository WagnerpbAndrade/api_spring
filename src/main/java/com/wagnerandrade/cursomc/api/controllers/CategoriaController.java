package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.CategoriaDTO;
import com.wagnerandrade.cursomc.api.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok(this.service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        CategoriaDTO categoria = this.service.getById(id);

        return ResponseEntity.ok(categoria);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Categoria categoria) {
        categoria = this.service.insert(categoria);

        return ResponseEntity.created(getUri(categoria)).build();
    }

    private URI getUri(Categoria categoria) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(categoria.getId()).toUri();
    }
}
