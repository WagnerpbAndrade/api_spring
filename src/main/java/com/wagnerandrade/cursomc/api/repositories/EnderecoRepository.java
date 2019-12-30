package com.wagnerandrade.cursomc.api.repositories;

import com.wagnerandrade.cursomc.api.model.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
