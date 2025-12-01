package com.ifsp.carrinho.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.carrinho.entity.Carrinho;
import com.ifsp.carrinho.service.CarrinhoService;

@RestController
@RequestMapping("/carrinho")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class CarrinhoController {

    private final CarrinhoService service;

    public CarrinhoController(CarrinhoService service) {
        this.service = service;
    }

    @GetMapping("/{idCliente}")
    public Carrinho verCarrinho(@PathVariable("idCliente") String idCliente) {
        return service.getCarrinho(idCliente);
    }

    @PostMapping("/{idCliente}/adicionar/{idLivro}")
    public Carrinho adicionar(
            @PathVariable("idCliente") String idCliente,
            @PathVariable("idLivro") String idLivro,
            @RequestParam("quantidade") int quantidade
    ) {
        return service.adicionarItem(idCliente, idLivro, quantidade);
    }

    @DeleteMapping("/{idCliente}/remover/{idItem}")
    public Carrinho remover(
            @PathVariable("idCliente") String idCliente,
            @PathVariable("idItem") String idItem
    ) {
        return service.removerItem(idCliente, idItem);
    }

    @DeleteMapping("/{idCliente}/limpar")
    public Carrinho limpar(@PathVariable("idCliente") String idCliente) {
        return service.limparCarrinho(idCliente);
    }
}