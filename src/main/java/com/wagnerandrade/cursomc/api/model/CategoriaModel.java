package com.wagnerandrade.cursomc.api.model;

import java.io.Serializable;
import java.util.Objects;

public class CategoriaModel implements Serializable {

   private static final long serialVersionUID = 1L;

   private Long id;

   private String nome;

   public CategoriaModel(){}

    public CategoriaModel(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategoriaModel that = (CategoriaModel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
