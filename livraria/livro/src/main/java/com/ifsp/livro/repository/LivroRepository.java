package com.ifsp.livro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ifsp.livro.entity.Livro;

@Repository
public interface LivroRepository extends JpaRepository<Livro, String> {
    
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    
    List<Livro> findByCategoria(String categoria);
    
    List<Livro> findByIsbn(String isbn);
}