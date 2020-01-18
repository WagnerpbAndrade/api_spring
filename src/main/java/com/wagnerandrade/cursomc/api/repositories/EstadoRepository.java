package com.wagnerandrade.cursomc.api.repositories;

import com.wagnerandrade.cursomc.api.model.Estado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

    @Transactional(readOnly=true)
    List<Estado> findAllByOrderByNome();
}
