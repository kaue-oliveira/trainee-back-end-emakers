package com.empresa.biblioteca.services;

import com.empresa.biblioteca.entities.Livro;
import com.empresa.biblioteca.repositories.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    // Salvar um livro
    public Livro saveLivro(Livro livro) {
        return livroRepository.save(livro);
    }

    // Obter todos os livros
    public List<Livro> getAllLivros() {
        return livroRepository.findAll();
    }

    // Obter livro por ID
    public Livro getLivroById(Long id) {
        Optional<Livro> livro = livroRepository.findById(id);
        return livro.orElse(null);
    }

    // Atualizar um livro
    public Livro updateLivro(Long id, Livro livro) {
        if (livroRepository.existsById(id)) {
            livro.setId(id);
            return livroRepository.save(livro);
        }
        return null;
    }

    // Deletar livro
    public boolean deleteLivro(Long id) {
        if (livroRepository.existsById(id)) {
            livroRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
