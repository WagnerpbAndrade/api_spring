package com.wagnerandrade.cursomc.api.model.dto;

import com.wagnerandrade.cursomc.api.model.Cidade;
import lombok.Data;
import org.modelmapper.ModelMapper;

import java.io.Serializable;

@Data
public class CidadeDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String nome;

    public CidadeDTO() {}

    public static CidadeDTO create(Cidade cidade) {
        return new ModelMapper().map(cidade, CidadeDTO.class);
    }
}
