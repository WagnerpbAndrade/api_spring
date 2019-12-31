package com.wagnerandrade.cursomc.api.repositories;

import com.wagnerandrade.cursomc.api.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
}
