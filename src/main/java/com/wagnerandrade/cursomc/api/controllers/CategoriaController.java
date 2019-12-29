package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.Categoria;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categorias")
public class CategoriaController {

    @GetMapping
    public List<Categoria> listar() {

        Categoria cat1 = new Categoria(1L, "Informática");

        Categoria cat2 = new Categoria(2L, "Escritório");

        List<Categoria> categorias = new ArrayList();
        categorias.add(cat1);
        categorias.add(cat2);

        return categorias;
    }
}
