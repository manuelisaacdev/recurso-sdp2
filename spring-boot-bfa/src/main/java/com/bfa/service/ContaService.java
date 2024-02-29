package com.bfa.service;

import java.util.UUID;

import com.bfa.exception.EntityNotFoundException;
import com.bfa.model.Conta;
import com.bfa.model.TipoConta;

public interface ContaService extends AbstractService<Conta, UUID> {
	public Conta findByIBAN(String iban) throws EntityNotFoundException;
	public Conta findByNumeroConta(String numeroConta) throws EntityNotFoundException;
	public Conta generate(TipoConta tipoConta);
	public void depositar(String numeroConta, Double montante);
	public void levantar(String numeroConta, Double montante);
	public void transferenciaInterna(String ibanContaOrigem, String ibanContaDestino, Double montante);
	public void createTransferenciaInterbancaria(String ibanContaOrigem, Double montante);
}
