package com.ifsp.pagamento.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.pagamento.entity.Pagamento;
import com.ifsp.pagamento.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class PagamentoController {

    private final PagamentoService service;

    public PagamentoController(PagamentoService service) {
        this.service = service;
    }

    @PostMapping("/pix/{idPedido}")
    public ResponseEntity<Pagamento> pagarPix(
            @PathVariable String idPedido,
            @RequestParam String chavePix
    ) {
        return ResponseEntity.ok(service.pagarComPix(idPedido, chavePix));
    }

    @PostMapping("/cartao/{idPedido}")
    public ResponseEntity<Pagamento> pagarCartao(
            @PathVariable String idPedido,
            @RequestParam String numeroCartao,
            @RequestParam String nomeTitular,
            @RequestParam int parcelas
    ) {
        return ResponseEntity.ok(service.pagarComCartao(idPedido, numeroCartao, nomeTitular, parcelas));
    }
}

