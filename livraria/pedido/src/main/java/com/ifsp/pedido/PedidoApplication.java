package com.ifsp.pedido;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.ifsp.pedido",
    "com.ifsp.carrinho",
    "com.ifsp.cliente",
    "com.ifsp.livro"
})
@EntityScan(basePackages = {
    "com.ifsp.pedido.entity",
    "com.ifsp.carrinho.entity",
    "com.ifsp.cliente.entity",
    "com.ifsp.livro.entity"
})
@EnableJpaRepositories(basePackages = {
    "com.ifsp.pedido.repository",
    "com.ifsp.carrinho.repository",
    "com.ifsp.cliente.repository",
    "com.ifsp.livro.repository"
})
public class PedidoApplication {
    public static void main(String[] args) {
        SpringApplication.run(PedidoApplication.class, args);
    }
}
