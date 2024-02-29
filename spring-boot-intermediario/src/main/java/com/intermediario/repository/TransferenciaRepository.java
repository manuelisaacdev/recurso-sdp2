package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.Transferencia;

public interface TransferenciaRepository extends JpaRepository<Transferencia, UUID> {

}
