package com.bfa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfa.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
	
}
