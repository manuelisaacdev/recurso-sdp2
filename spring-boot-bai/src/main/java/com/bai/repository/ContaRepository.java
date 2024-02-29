package com.bai.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.model.Conta;


public interface ContaRepository extends JpaRepository<Conta, UUID> {
	public Optional<Conta> findByIban(String iban);
}
