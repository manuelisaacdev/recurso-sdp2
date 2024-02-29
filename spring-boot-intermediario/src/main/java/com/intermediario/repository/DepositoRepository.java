package com.intermediario.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intermediario.model.Deposito;


public interface DepositoRepository extends JpaRepository<Deposito, UUID> {

}
