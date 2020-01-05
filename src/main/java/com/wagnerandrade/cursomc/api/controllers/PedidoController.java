package com.wagnerandrade.cursomc.api.controllers;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.Pedido;
import com.wagnerandrade.cursomc.api.model.dto.CategoriaDTO;
import com.wagnerandrade.cursomc.api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/api/v1/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Pedido pedido = this.service.getById(id);

        return ResponseEntity.ok(pedido);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody Pedido obj) {
        obj = this.service.insert(obj);

        return ResponseEntity.created(getUri(obj)).build();
    }

    private URI getUri(Pedido pedido) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(pedido.getId()).toUri();
    }
}