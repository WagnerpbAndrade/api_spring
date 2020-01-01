package com.wagnerandrade.cursomc.api.controllers;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.wagnerandrade.cursomc.api.model.Pedido;
import com.wagnerandrade.cursomc.api.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}