package com.wagnerandrade.cursomc.api.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CredenciaisDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String senha;

    public CredenciaisDTO() {
    }

}
