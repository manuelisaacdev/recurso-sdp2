package com.bai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.model.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {

}
