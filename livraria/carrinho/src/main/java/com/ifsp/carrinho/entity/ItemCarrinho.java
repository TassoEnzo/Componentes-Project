package com.ifsp.carrinho.entity;

import com.ifsp.livro.entity.Livro;
import jakarta.persistence.*;

@Entity
@Table(name = "itens_carrinho")
public class ItemCarrinho {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Livro livro;

    private int quantidade;

    private double subtotal;

    public ItemCarrinho() {}

    public ItemCarrinho(Livro livro, int quantidade) {
        this.livro = livro;
        this.quantidade = quantidade;
        this.subtotal = livro.getPreco() * quantidade;
    }

    public String getId() { return id; }

    public Livro getLivro() { return livro; }

    public int getQuantidade() { return quantidade; }

    public double getSubtotal() { return subtotal; }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
        this.subtotal = livro.getPreco() * quantidade;
    }
}
