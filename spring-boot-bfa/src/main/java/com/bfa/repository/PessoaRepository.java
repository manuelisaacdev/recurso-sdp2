package com.bfa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfa.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

}
