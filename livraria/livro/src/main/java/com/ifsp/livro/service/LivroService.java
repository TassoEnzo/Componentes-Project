package com.ifsp.livro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ifsp.livro.entity.Livro;
import com.ifsp.livro.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository repository;

    public LivroService(LivroRepository repository) {
        this.repository = repository;
    }

    public List<Livro> pesquisar(String campo, String termo) {
        return switch (campo.toLowerCase()) {
            case "titulo" -> repository.findByTituloContainingIgnoreCase(termo);
            case "autor" -> repository.findByAutorContainingIgnoreCase(termo);
            case "categoria" -> repository.findByCategoria(termo);
            default -> repository.findAll();
        };
    }

    public void diminuirEstoque(String idLivro, int qtd) {
        Livro livro = buscarPorId(idLivro);

    if (livro.getEstoque() < qtd) {
        throw new RuntimeException("Estoque insuficiente para o livro: " + livro.getTitulo());
    }

        livro.setEstoque(livro.getEstoque() - qtd);
        repository.save(livro);
    }


    public Livro buscarPorId(String id) {
    return repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
}

    public Livro salvar(Livro livro) {
        return repository.save(livro);
    }

    public void deletarPorId(String id) {
        repository.deleteById(id);
    }

    public List<Livro> listarTodos() {
        return repository.findAll();
    }
}
