package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Optional<Categoria> getById(Long id) {
        return this.repository.findById(id);
    }

    public List<Categoria> getAll() {
        return this.repository.findAll();
    }
}
