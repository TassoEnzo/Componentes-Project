package com.ifsp.gateway.controller;

import com.ifsp.gateway.service.GatewayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gateway")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class GatewayController {

    private final GatewayService service;

    public GatewayController(GatewayService service) {
        this.service = service;
    }

    @GetMapping("/livros")
    public ResponseEntity<?> listarLivros() {
        return ResponseEntity.ok(service.listarLivros());
    }

    @GetMapping("/livros/{id}")
    public ResponseEntity<?> buscarLivro(@PathVariable String id) {
        return ResponseEntity.ok(service.buscarLivro(id));
    }

    @PostMapping("/carrinho/{idCliente}/add/{idLivro}/{qtd}")
    public ResponseEntity<?> adicionarAoCarrinho(
            @PathVariable String idCliente,
            @PathVariable String idLivro,
            @PathVariable int qtd) {

        return ResponseEntity.ok(service.adicionarAoCarrinho(idCliente, idLivro, qtd));
    }

    @PostMapping("/pedido/{idCliente}")
    public ResponseEntity<?> criarPedido(@PathVariable String idCliente) {
        return ResponseEntity.ok(service.criarPedido(idCliente));
    }

    @GetMapping("/frete/{cep}")
    public ResponseEntity<?> calcularFrete(@PathVariable String cep) {
        return ResponseEntity.ok(service.calcularFrete(cep));
    }

    @PostMapping("/pagamento")
    public ResponseEntity<?> pagar(
            @RequestParam String pedido,
            @RequestParam String metodo,
            @RequestParam String chave) {

        return ResponseEntity.ok(service.pagarPedido(pedido, metodo, chave));
    }
}
