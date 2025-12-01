package com.ifsp.pagamento.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamento_cartao")
public class PagamentoCartao extends Pagamento {

    private String numeroCartao;
    private String nomeTitular;
    private int parcelas;

    public PagamentoCartao() {}

    public PagamentoCartao(String idPedido,
                           double valorPago,
                           String numeroCartao,
                           String nomeTitular,
                           int parcelas) {
        super(idPedido, valorPago, TipoPagamento.CARTAO);
        this.numeroCartao = numeroCartao;
        this.nomeTitular = nomeTitular;
        this.parcelas = parcelas;
    }

    public String getNumeroCartao() { return numeroCartao; }
    public void setNumeroCartao(String numeroCartao) { this.numeroCartao = numeroCartao; }
    public String getNomeTitular() { return nomeTitular; }
    public void setNomeTitular(String nomeTitular) { this.nomeTitular = nomeTitular; }
    public int getParcelas() { return parcelas; }
    public void setParcelas(int parcelas) { this.parcelas = parcelas; }
}