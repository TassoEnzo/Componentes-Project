package com.ifsp.cliente.controller;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.cliente.entity.Cliente;
import com.ifsp.cliente.service.ClienteService;

@RestController
@RequestMapping("/clientes")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ClienteController {

    private static final Logger logger = LoggerFactory.getLogger(ClienteController.class);
    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public List<Cliente> listar() {
        logger.info("GET /clientes - Listando clientes");
        return service.listar();
    }

    @GetMapping("/{id}")
    public Cliente buscar(@PathVariable String id) {
        logger.info("GET /clientes/{} - Buscando cliente", id);
        return service.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente cadastrar(@RequestBody Cliente cliente) {
        logger.info("POST /clientes - Recebido: {}", cliente);
        logger.info("Nome: {}, Email: {}, CPF: {}, Telefone: {}, Senha: {}", 
            cliente.getNome(), 
            cliente.getEmail(), 
            cliente.getCpf(), 
            cliente.getTelefone(),
            cliente.getSenha() != null ? "***" : "null");
        
        try {
            Cliente salvo = service.cadastrar(cliente);
            logger.info("Cliente cadastrado com sucesso: {}", salvo.getId());
            salvo.setSenha(null);
            return salvo;
        } catch (Exception e) {
            logger.error("Erro ao cadastrar cliente: ", e);
            throw e;
        }
    }

    @PostMapping("/login")
    public Cliente login(@RequestBody Map<String, String> credentials) {
        logger.info("POST /clientes/login - Tentativa de login");
        
        String email = credentials.get("email");
        String senha = credentials.get("senha");
        
        logger.info("Email: {}", email);
        
        Cliente cliente = service.buscarPorEmail(email);
        
        if (cliente != null && cliente.getSenha().equals(senha)) {
            logger.info("Login bem-sucedido para: {}", email);

            cliente.setSenha(null);
            return cliente;
        }
        
        logger.warn("Login falhou para: {}", email);
        throw new RuntimeException("Email ou senha inválidos");
    }

    @PutMapping("/{id}")
    public Cliente atualizar(@PathVariable String id, @RequestBody Cliente cliente) {
        logger.info("PUT /clientes/{} - Atualizando cliente", id);
        return service.atualizar(id, cliente);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable String id) {
        logger.info("DELETE /clientes/{} - Deletando cliente", id);
        service.deletar(id);
    }
}
