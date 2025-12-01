package com.ifsp.carrinho.repository;

import com.ifsp.carrinho.entity.ItemCarrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, String> {
}
