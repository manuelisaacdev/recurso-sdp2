package com.bfa.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfa.model.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, UUID> {
	public Optional<Funcionario> findByEmail(String email);
}
