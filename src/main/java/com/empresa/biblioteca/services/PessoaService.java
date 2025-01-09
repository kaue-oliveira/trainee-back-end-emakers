package com.empresa.biblioteca.services;

import com.empresa.biblioteca.entities.Pessoa;
import com.empresa.biblioteca.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    // Salvar uma pessoa
    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    // Obter todas as pessoas
    public List<Pessoa> getAllPessoas() {
        return pessoaRepository.findAll();
    }

    // Obter pessoa por ID
    public Pessoa getPessoaById(Long id) {
        Optional<Pessoa> pessoa = pessoaRepository.findById(id);
        return pessoa.orElse(null);
    }

    // Atualizar uma pessoa
    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
        if (pessoaRepository.existsById(id)) {
            pessoa.setId(id);
            return pessoaRepository.save(pessoa);
        }
        return null;
    }

    // Deletar pessoa
    public boolean deletePessoa(Long id) {
        if (pessoaRepository.existsById(id)) {
            pessoaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
