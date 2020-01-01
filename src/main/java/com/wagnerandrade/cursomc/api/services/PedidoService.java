package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.Pedido;
import com.wagnerandrade.cursomc.api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }
}
