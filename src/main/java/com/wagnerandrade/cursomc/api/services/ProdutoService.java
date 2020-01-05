package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.Produto;
import com.wagnerandrade.cursomc.api.model.dto.ProdutoDTO;
import com.wagnerandrade.cursomc.api.repositories.CategoriaRepository;
import com.wagnerandrade.cursomc.api.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    public Page<ProdutoDTO> search(String nome, List<Long> ids, Integer page, Integer linesPerPage, String direction, String orderBy) {
        PageRequest pages = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Categoria> categorias = this.categoriaRepository.findAllById(ids);

        return this.repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pages).map(ProdutoDTO::create);
    }
}
