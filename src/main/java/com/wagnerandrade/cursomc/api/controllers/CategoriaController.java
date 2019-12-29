package com.wagnerandrade.cursomc.api.controllers;

import com.wagnerandrade.cursomc.api.model.CategoriaModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/categorias")
public class CategoriaController {

    @GetMapping
    public List<CategoriaModel> listar() {

        CategoriaModel cat1 = new CategoriaModel(1L, "Informática");

        CategoriaModel cat2 = new CategoriaModel(2L, "Escritório");

        List<CategoriaModel> categorias = new ArrayList();
        categorias.add(cat1);
        categorias.add(cat2);

        return categorias;
    }
}
