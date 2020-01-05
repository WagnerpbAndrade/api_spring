package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.controllers.utils.URL;
import com.wagnerandrade.cursomc.api.model.Produto;
import com.wagnerandrade.cursomc.api.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable("id") Long id) {
        Produto produto = this.service.getById(id);

        return ResponseEntity.ok(produto);
    }

    @GetMapping()
    public ResponseEntity getPage(
            @RequestParam(value = "nome", defaultValue = "") String nome,
            @RequestParam(value = "categorias", defaultValue = "") String categorias,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy) {

        String nomeDecoded = URL.decodeParam(nome);
        List<Long> ids = URL.decodeLongList(categorias);
        return ResponseEntity.ok().body(this.service.search(nomeDecoded, ids, page, linesPerPage, direction, orderBy));
    }
}