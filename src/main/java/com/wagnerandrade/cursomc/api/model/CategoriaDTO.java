package com.wagnerandrade.cursomc.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;

    private CategoriaDTO() {}

    public static CategoriaDTO create(Categoria categoria) {
        ModelMapper modelMapper = new ModelMapper();
        CategoriaDTO dto = modelMapper.map(categoria, CategoriaDTO.class);

        return dto;
    }
}
