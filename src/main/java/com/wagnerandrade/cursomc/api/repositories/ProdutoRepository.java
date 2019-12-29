package com.wagnerandrade.cursomc.api.repositories;

import com.wagnerandrade.cursomc.api.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
