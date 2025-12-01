package com.ifsp.carrinho.entity;

import com.ifsp.cliente.entity.Cliente;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carrinhos")
public class Carrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @OneToOne
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrinho> itens = new ArrayList<>();

    private double total;

    public Carrinho() {}

    public Carrinho(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getId() { return id; }

    public Cliente getCliente() { return cliente; }

    public List<ItemCarrinho> getItens() { return itens; }

    public double getTotal() { return total; }

    public void recalcularTotal() {
        total = itens.stream().mapToDouble(ItemCarrinho::getSubtotal).sum();
    }
}

