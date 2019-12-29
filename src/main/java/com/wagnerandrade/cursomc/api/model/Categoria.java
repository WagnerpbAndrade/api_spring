package com.wagnerandrade.cursomc.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Categoria implements Serializable {

   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String nome;

   @ManyToMany(mappedBy = "categorias")
   private List<Produto> produtos = new ArrayList<>();

    public Categoria() {}

    public Categoria(long l, String nome) {
        this.id = id;
        this.nome = nome;
    }
}
