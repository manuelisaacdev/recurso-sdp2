package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.HistoricoBancario;

public interface HistoricoBancarioRepository extends JpaRepository<HistoricoBancario, UUID> {

}
