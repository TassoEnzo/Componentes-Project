package com.ifsp.pagamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento_pix")
public class PagamentoPix extends Pagamento {

    private String chavePix;

    public PagamentoPix() {}

    public PagamentoPix(String idPedido, double valorPago, String chavePix) {
        super(idPedido, valorPago, TipoPagamento.PIX);
        this.chavePix = chavePix;
    }

    public String getChavePix() { return chavePix; }
    public void setChavePix(String chavePix) { this.chavePix = chavePix; }
}