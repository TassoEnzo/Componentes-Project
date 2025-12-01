package com.ifsp.frete.entity;

public class Frete {

    private String cep;
    private double valor;

    public Frete(String cep, double valor) {
        this.cep = cep;
        this.valor = valor;
    }

    public String getCep() { return cep; }
    public double getValor() { return valor; }
}
