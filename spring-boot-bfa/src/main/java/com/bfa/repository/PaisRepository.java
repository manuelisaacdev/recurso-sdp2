package com.bfa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfa.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, UUID> {

}
