package com.bai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.model.Telefone;

public interface TelefoneRepository extends JpaRepository<Telefone, UUID> {
	
}
