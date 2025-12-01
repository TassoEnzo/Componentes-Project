package com.ifsp.carrinho;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.ifsp.carrinho",
    "com.ifsp.cliente",
    "com.ifsp.livro"
})
@EntityScan(basePackages = {
    "com.ifsp.carrinho.entity",
    "com.ifsp.cliente.entity",
    "com.ifsp.livro.entity"
})
public class CarrinhoApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarrinhoApplication.class, args);
    }
}
