package com.bai.service;

import java.util.UUID;

import com.bai.exception.EntityNotFoundException;
import com.bai.model.Conta;
import com.bai.model.TipoConta;

public interface ContaService extends AbstractService<Conta, UUID> {
	public Conta findByIBAN(String iban) throws EntityNotFoundException;
	public Conta findByNumeroConta(String numeroConta) throws EntityNotFoundException;
	public Conta generate(TipoConta tipoConta);
	public void depositar(String numeroConta, Double montante);
	public void levantar(String numeroConta, Double montante);
	public void transferenciaInterna(String ibanContaOrigem, String ibanContaDestino, Double montante);
	public void createTransferenciaInterbancaria(String ibanContaOrigem, Double montante);
}
