package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.Levantamento;

public interface LevantamentoRepository extends JpaRepository<Levantamento, UUID> {

}
