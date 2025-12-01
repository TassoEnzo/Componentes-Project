package com.ifsp.pagamento.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "pagamento")
public abstract class Pagamento {

    @Id
    private String id;

    private String idPedido;

    private double valorPago;

    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipo;

    public Pagamento() {}

    public Pagamento(String idPedido, double valorPago, TipoPagamento tipo) {
        this.id = java.util.UUID.randomUUID().toString();
        this.idPedido = idPedido;
        this.valorPago = valorPago;
        this.dataPagamento = LocalDateTime.now();
        this.tipo = tipo;
    }

    public enum TipoPagamento {
        CARTAO,
        PIX
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getIdPedido() { return idPedido; }
    public void setIdPedido(String idPedido) { this.idPedido = idPedido; }
    public double getValorPago() { return valorPago; }
    public void setValorPago(double valorPago) { this.valorPago = valorPago; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public TipoPagamento getTipo() { return tipo; }
}