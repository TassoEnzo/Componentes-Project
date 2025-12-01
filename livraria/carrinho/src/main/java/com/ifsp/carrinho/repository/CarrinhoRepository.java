package com.ifsp.carrinho.repository;

import com.ifsp.carrinho.entity.Carrinho;
import com.ifsp.cliente.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, String> {
    Optional<Carrinho> findByCliente(Cliente cliente);
}

