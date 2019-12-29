package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.services.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Categoria> op = this.service.getById(id);

        Categoria categoria = op.get();

        if (categoria != null) {
            return ResponseEntity.ok(this.service.getById(id));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
