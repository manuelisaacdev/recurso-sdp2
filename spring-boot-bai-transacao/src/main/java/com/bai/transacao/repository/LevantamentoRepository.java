package com.bai.transacao.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.transacao.model.Levantamento;

public interface LevantamentoRepository extends JpaRepository<Levantamento, UUID> {
	public Optional<Levantamento> findByNumeroOrdem(String numeroOrdem);
}
