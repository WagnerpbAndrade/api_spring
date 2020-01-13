package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Pedido;
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

    @GetMapping
    public ResponseEntity getPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "DESC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "instante") String orderBy) {
        return ResponseEntity.ok().body(this.service.findPage(page, linesPerPage, direction, orderBy));
    }
}