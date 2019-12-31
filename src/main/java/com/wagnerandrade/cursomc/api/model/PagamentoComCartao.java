package com.wagnerandrade.cursomc.api.model;

import com.wagnerandrade.cursomc.api.model.enums.EstadoPagamento;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class PagamentoComCartao extends Pagamento {

    private static final long serialVersionUID = 1L;

    private Integer numeroParcelas;

    public PagamentoComCartao() {
    }

    public PagamentoComCartao(Long id, EstadoPagamento estado, Pedido pedido, Integer numeroParcelas) {
        super(id, estado, pedido);
        this.numeroParcelas = numeroParcelas;
    }
}
