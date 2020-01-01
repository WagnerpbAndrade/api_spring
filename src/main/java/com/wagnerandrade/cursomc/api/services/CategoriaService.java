package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.infra.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.CategoriaDTO;
import com.wagnerandrade.cursomc.api.repositories.CategoriaRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public CategoriaDTO getById(Long id) {
        return this.repository.findById(id).map(CategoriaDTO::create).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    public List<Categoria> getAll() {
        return this.repository.findAll();
    }

    public Categoria insert(Categoria categoria) {
        Assert.notNull("id", "Não foi possível inserir a categoria");

        return this.repository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        return this.repository.save(categoria);
    }
}
