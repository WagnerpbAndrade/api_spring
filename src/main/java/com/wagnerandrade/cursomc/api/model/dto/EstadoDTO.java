package com.wagnerandrade.cursomc.api.model.dto;

import com.wagnerandrade.cursomc.api.model.Estado;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
public class EstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    public EstadoDTO() {
    }

    public static EstadoDTO create(Estado estado) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(estado, EstadoDTO.class);
    }
}
