package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
	
}
