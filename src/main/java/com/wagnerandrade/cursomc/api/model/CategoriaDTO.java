package com.wagnerandrade.cursomc.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoriaDTO {

    private Long id;
    private String nome;
    private Double preco;
    private List<Produto> produtos;

    private CategoriaDTO() {}

    public static CategoriaDTO create(Categoria categoria) {
        ModelMapper modelMapper = new ModelMapper();
        CategoriaDTO dto = modelMapper.map(categoria, CategoriaDTO.class);

        return dto;
    }
}
