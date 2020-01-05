package com.wagnerandrade.cursomc.api.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wagnerandrade.cursomc.api.model.Categoria;
import com.wagnerandrade.cursomc.api.model.Produto;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProdutoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nome;
    private Double preco;

    private ProdutoDTO() {}

    public static ProdutoDTO create(Produto produto) {
        ModelMapper modelMapper = new ModelMapper();
        ProdutoDTO dto = modelMapper.map(produto, ProdutoDTO.class);

        return dto;
    }
}
