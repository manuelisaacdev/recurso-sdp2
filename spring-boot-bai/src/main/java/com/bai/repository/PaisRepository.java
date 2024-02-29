package com.bai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.model.Pais;

public interface PaisRepository extends JpaRepository<Pais, UUID> {

}
