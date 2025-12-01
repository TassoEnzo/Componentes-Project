package com.ifsp.pedido.entity;

import jakarta.persistence.*;

@Entity
public class ItemPedido {

    @Id
    private String id;

    private String idLivro;
    private String titulo;
    private int quantidade;
    private double precoUnitario;
    private double subtotal;

    public ItemPedido() {}

    public ItemPedido(String idLivro, String titulo, int quantidade, double precoUnitario) {
        this.id = java.util.UUID.randomUUID().toString();
        this.idLivro = idLivro;
        this.titulo = titulo;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.subtotal = quantidade * precoUnitario;
    }

    public String getId() { return id; }

    public String getIdLivro() { return idLivro; }
    
    public String getTitulo() { return titulo; }
    
    public int getQuantidade() { return quantidade; }
    
    public double getPrecoUnitario() { return precoUnitario; }
    
    public double getSubtotal() { return subtotal; }
}
    