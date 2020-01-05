package com.wagnerandrade.cursomc.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.wagnerandrade.cursomc.api.model.enums.EstadoPagamento;
import lombok.Data;

import javax.persistence.Entity;
import java.util.Date;

@Entity
@Data
@JsonTypeName("pagamentoComBoleto")
public class PagamentoComBoleto extends Pagamento{

    private static final long serialVersionUID = 1L;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dataPagamento;

    public PagamentoComBoleto() {
    }

    public PagamentoComBoleto(Long id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
        super(id, estado, pedido);
        this.dataVencimento = dataVencimento;
        this.dataPagamento = dataPagamento;
    }
}
