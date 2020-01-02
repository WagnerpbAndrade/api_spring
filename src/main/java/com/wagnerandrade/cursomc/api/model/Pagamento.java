package com.wagnerandrade.cursomc.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wagnerandrade.cursomc.api.model.enums.EstadoPagamento;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private Integer estado;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "pedido_id")
    @MapsId
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Long id, EstadoPagamento estado, Pedido pedido) {
        this.id = id;
        this.estado = (estado == null) ? null : estado.getCod();
        this.pedido = pedido;
    }

    public EstadoPagamento getEstado() {
        return EstadoPagamento.toEnum(estado);
    }

    public void setEstado(EstadoPagamento estado) {
        this.estado = estado.getCod();
    }
}
