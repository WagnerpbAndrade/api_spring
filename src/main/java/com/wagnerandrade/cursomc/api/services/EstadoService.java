package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.model.dto.EstadoDTO;
import com.wagnerandrade.cursomc.api.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public List<EstadoDTO> getALL() {
        return this.estadoRepository.findAllByOrderByNome().stream().map(EstadoDTO::create).collect(Collectors.toList());
    }
}
