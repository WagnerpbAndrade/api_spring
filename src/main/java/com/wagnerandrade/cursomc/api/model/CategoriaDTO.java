package com.wagnerandrade.cursomc.api.model;

import org.modelmapper.ModelMapper;

public class CategoriaDTO {

    private Long id;
    private String nome;
    private Double preco;

    private CategoriaDTO() {}

    public static CategoriaDTO create(Categoria categoria) {
        ModelMapper mm = new ModelMapper();
        return mm.map(categoria, CategoriaDTO.class);
    }
}
