package com.empresa.biblioteca.controllers;

import com.empresa.biblioteca.entities.Livro;
import com.empresa.biblioteca.services.LivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@SuppressWarnings("unused")
@RestController
@RequestMapping("/api/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // Criar um novo livro
    @PostMapping
    public ResponseEntity<Livro> createLivro(@Valid @RequestBody Livro livro) {
        Livro createdLivro = livroService.saveLivro(livro);
        return new ResponseEntity<>(createdLivro, HttpStatus.CREATED);
    }

    // Obter todos os livros
    @GetMapping
    public ResponseEntity<List<Livro>> getAllLivros() {
        List<Livro> livros = livroService.getAllLivros();
        return new ResponseEntity<>(livros, HttpStatus.OK);
    }

    // Obter livro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Livro> getLivroById(@PathVariable Long id) {
        Livro livro = livroService.getLivroById(id);
        if (livro != null) {
            return new ResponseEntity<>(livro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Atualizar livro
    @PutMapping("/{id}")
    public ResponseEntity<Livro> updateLivro(@PathVariable Long id, @Valid @RequestBody Livro livro) {
        Livro updatedLivro = livroService.updateLivro(id, livro);
        if (updatedLivro != null) {
            return new ResponseEntity<>(updatedLivro, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Deletar livro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivro(@PathVariable Long id) {
        boolean isDeleted = livroService.deleteLivro(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
