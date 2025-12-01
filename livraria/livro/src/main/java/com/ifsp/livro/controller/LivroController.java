package com.ifsp.livro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ifsp.livro.entity.Livro;
import com.ifsp.livro.service.LivroService;

@RestController
@RequestMapping("/livros")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class LivroController {

    private final LivroService service;

    public LivroController(LivroService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Livro>> listar() {
        return ResponseEntity.ok(service.listarTodos());
    }

    @GetMapping("/pesquisar")
    public ResponseEntity<List<Livro>> pesquisar(
            @RequestParam(defaultValue = "titulo") String campo,
            @RequestParam String termo
    ) {
        return ResponseEntity.ok(service.pesquisar(campo, termo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livro> detalhes(@PathVariable String id) {
        Livro livro = service.buscarPorId(id);
        return ResponseEntity.ok(livro);
    }

    @PostMapping
    public ResponseEntity<Livro> cadastrar(@RequestBody Livro livro) {
        return ResponseEntity.ok(service.salvar(livro));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable String id) {
        service.deletarPorId(id);
        return ResponseEntity.noContent().build();
    }
}
