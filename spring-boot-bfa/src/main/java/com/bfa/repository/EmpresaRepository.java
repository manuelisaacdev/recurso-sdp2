package com.bfa.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bfa.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

}
