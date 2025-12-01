package com.ifsp.pedido.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.pedido.entity.Pedido;
import com.ifsp.pedido.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping("/criar/{idCliente}")
    public ResponseEntity<Pedido> criar(@PathVariable String idCliente) {
        return ResponseEntity.ok(service.criarPedidoDesdeCarrinho(idCliente));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Pedido>> listarPorCliente(@PathVariable String idCliente) {
        return ResponseEntity.ok(service.listarPorCliente(idCliente));
    }

    @PutMapping("/{idPedido}/status/{status}")
    public ResponseEntity<Pedido> atualizar(@PathVariable String idPedido,
                                            @PathVariable Pedido.StatusPedido status) {
        return ResponseEntity.ok(service.atualizarStatus(idPedido, status));
    }
}
