package com.ifsp.pedido.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    private String id;

    private String idCliente;

    private LocalDateTime dataCriacao;

    private double valorTotal;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    public Pedido() {}

    public Pedido(String idCliente) {
        this.id = java.util.UUID.randomUUID().toString();
        this.idCliente = idCliente;
        this.dataCriacao = LocalDateTime.now();
        this.status = StatusPedido.CRIADO;
    }

    public void adicionarItem(ItemPedido item) {
        itens.add(item);
        valorTotal += item.getSubtotal();
    }
    
    public String getId() { return id; }
    
    public String getIdCliente() { return idCliente; }
    
    public LocalDateTime getDataCriacao() { return dataCriacao; }
    
    public double getValorTotal() { return valorTotal; }
    
    public StatusPedido getStatus() { return status; }

    public void setStatus(StatusPedido status) { this.status = status;} 
    
    public List<ItemPedido> getItens() { return itens; }

    public enum StatusPedido {
        CRIADO,
        PAGO,
        ENVIADO,
        CANCELADO
    }
}
