package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.infra.exception.DataIntregratyException;
import com.wagnerandrade.cursomc.api.infra.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.CategoriaDTO;
import com.wagnerandrade.cursomc.api.repositories.CategoriaRepository;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    public List<CategoriaDTO> getAll() {
        return this.repository.findAll().stream().map(CategoriaDTO::create).collect(Collectors.toList());
    }

    public Categoria insert(Categoria categoria) {
        Assert.notNull("id", "Não foi possível inserir a categoria");

        return this.repository.save(categoria);
    }

    public Categoria update(Categoria categoria) {
        return this.repository.save(categoria);
    }

    public void delete(Long id) {
        try{
            this.repository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntregratyException("Não é possível excluir uma cat");
        }
    }

    public Page<CategoriaDTO> findPage(Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pages = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return this.repository.findAll(pages).map(CategoriaDTO::create);
    }
}
