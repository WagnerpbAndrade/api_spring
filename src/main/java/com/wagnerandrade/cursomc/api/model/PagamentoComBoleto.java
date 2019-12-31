package com.wagnerandrade.cursomc.api.model;

import com.wagnerandrade.cursomc.api.model.enums.EstadoPagamento;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
public class PagamentoComBoleto extends Pagamento{

    private static final long serialVersionUID = 1L;

    private Date dataVencimento;

    private Date dataPagamento;

    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(Long id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }
}
