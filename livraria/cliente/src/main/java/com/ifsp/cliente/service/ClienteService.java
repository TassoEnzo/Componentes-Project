package com.ifsp.cliente.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.cliente.entity.Cliente;
import com.ifsp.cliente.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository) {
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Cliente buscarPorId(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
    }

    public Cliente buscarPorEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email ou senha inválidos"));
    }

    public Cliente cadastrar(Cliente cliente) {
        if (repository.findByEmail(cliente.getEmail()).isPresent())
            throw new RuntimeException("E-mail já cadastrado");

        if (cliente.getCpf() != null && !cliente.getCpf().isEmpty()) {
            if (repository.findByCpf(cliente.getCpf()).isPresent())
                throw new RuntimeException("CPF já cadastrado");
        }

        return repository.save(cliente);
    }

    public Cliente atualizar(String id, Cliente dados) {
        Cliente cliente = buscarPorId(id);

        cliente.setNome(dados.getNome());
        cliente.setEmail(dados.getEmail());
        cliente.setSenha(dados.getSenha());
        cliente.setCpf(dados.getCpf());
        cliente.setTelefone(dados.getTelefone());

        return repository.save(cliente);
    }

    public void deletar(String id) {
        repository.deleteById(id);
    }
}