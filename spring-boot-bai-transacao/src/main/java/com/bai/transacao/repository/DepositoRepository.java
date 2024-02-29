package com.bai.transacao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.transacao.model.Deposito;


public interface DepositoRepository extends JpaRepository<Deposito, UUID> {
	public Optional<Deposito> findByNumeroOrdem(String numeroOrdem);
}
