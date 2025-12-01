package com.ifsp.frete.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.frete.entity.Frete;
import com.ifsp.frete.service.FreteService;

@RestController
@RequestMapping("/frete")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class FreteController {

    private final FreteService service;

    public FreteController(FreteService service) {
        this.service = service;
    }

    @GetMapping("/{cep}")
    public ResponseEntity<Frete> calcular(@PathVariable("cep") String cep) {
        return ResponseEntity.ok(service.calcularFrete(cep));
    }
}