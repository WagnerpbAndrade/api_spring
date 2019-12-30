package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.infra.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Cliente;
import com.wagnerandrade.cursomc.api.repositories.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    public Cliente getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Cliente não encontrado"));
    }
}
