package com.wagnerandrade.cursomc.api.services;

import com.wagnerandrade.cursomc.api.cotrollers.exception.ObjectNotFoundException;
import com.wagnerandrade.cursomc.api.model.ItemPedido;
import com.wagnerandrade.cursomc.api.model.PagamentoComBoleto;
import com.wagnerandrade.cursomc.api.model.Pedido;
import com.wagnerandrade.cursomc.api.model.Produto;
import com.wagnerandrade.cursomc.api.model.enums.EstadoPagamento;
import com.wagnerandrade.cursomc.api.repositories.ItemPedidoRepository;
import com.wagnerandrade.cursomc.api.repositories.PagamentoRepository;
import com.wagnerandrade.cursomc.api.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private BoletoService boletoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private ProdutoService produtoService;

    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Autowired
    private ClienteService clienteService;

    public Pedido getById(Long id) {
        return this.repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado!"));
    }

    @Transactional
    public Pedido insert(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj.setCliente(this.clienteService.getById(obj.getCliente().getId()));
        obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
        obj.getPagamento().setPedido(obj);
        if (obj.getPagamento() instanceof PagamentoComBoleto) {
            PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
            boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
        }
        obj = this.repository.save(obj);
        this.pagamentoRepository.save(obj.getPagamento());
        for (ItemPedido ip : obj.getItens()) {
            ip.setDesconto(0.0);
            ip.setProduto(this.produtoService.getById(ip.getProduto().getId()));
            ip.setPreco(ip.getProduto().getPreco());
            ip.setPedido(obj);
        }
        this.itemPedidoRepository.saveAll(obj.getItens());
        System.out.println(obj);
        return obj;
    }
}
