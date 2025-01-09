package com.empresa.biblioteca.repositories;

import com.empresa.biblioteca.entities.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
