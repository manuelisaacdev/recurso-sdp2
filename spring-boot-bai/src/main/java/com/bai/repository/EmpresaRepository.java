package com.bai.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bai.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, UUID> {

}
