package com.ifsp.cliente.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifsp.cliente.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, String> {

    Optional<Cliente> findByEmail(String email);
    Optional<Cliente> findByCpf(String cpf);

}
