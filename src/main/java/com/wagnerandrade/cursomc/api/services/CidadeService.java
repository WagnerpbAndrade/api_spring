package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.model.dto.CidadeDTO;
import com.wagnerandrade.cursomc.api.repositories.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository repository;

    public List<CidadeDTO> findByEstado(Long id) {
        return this.repository.findCidades(id).stream().map(CidadeDTO::create).collect(Collectors.toList());
    }
}
