package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, UUID> {

}
