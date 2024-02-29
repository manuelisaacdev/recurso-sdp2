package com.intermediario.service;

public interface StatusContaService {
	public String findConta(String iban);
	public String findAllDeposito(String iban);
	public String findAllLevantamento(String iban);
	public String findAllTransferencia(String iban);
}
