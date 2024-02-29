package com.bfa.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.bfa.exception.EntityNotFoundException;
import com.bfa.kafka.KafkaConsumerService;
import com.bfa.kafka.KafkaProducerService;
import com.bfa.kafka.KafkaTopicConfig;
import com.bfa.kafka.StatusTransacaoPayload;
import com.bfa.kafka.StatusTransferenciaInterbancariaPayload;
import com.bfa.kafka.StatusTransferenciaPayload;
import com.bfa.kafka.TransacaoPayload;
import com.bfa.kafka.TransferenciaInterbancariaPayload;
import com.bfa.kafka.TransferenciaPayload;
import com.bfa.model.Conta;
import com.bfa.model.Status;
import com.bfa.model.TipoConta;
import com.bfa.repository.ContaRepository;
import com.bfa.service.ContaService;
import com.bfa.util.PasswordGeneratorManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContaServiceImpl extends AbstractServiceImpl<Conta, UUID, ContaRepository> implements ContaService, KafkaConsumerService {
	private final String prefixIBAN;
	private final Integer sizeAccountNumber;
	private final KafkaProducerService kafkaProducerService;
	private final PasswordGeneratorManager passwordGeneratorManager;

	public ContaServiceImpl(ContaRepository repository, HttpServletRequest request, 
			MessageSource messageSource, @Value("${application.bfa.prefix-iban}") String prefixIBAN, 
			@Value("${application.bfa.size-account-number}") Integer sizeAccountNumber, 
			KafkaProducerService kafkaProducerService, PasswordGeneratorManager passwordGeneratorManager) {
		super(repository, request, messageSource);
		this.prefixIBAN = prefixIBAN;
		this.sizeAccountNumber = sizeAccountNumber;
		this.kafkaProducerService = kafkaProducerService;
		this.passwordGeneratorManager = passwordGeneratorManager;
	}
	
	@Override
	public Conta findByNumeroConta(String numeroConta) throws EntityNotFoundException {
		return super.findOne(Example.of(Conta.builder().numeroConta(numeroConta).build()));
	}
	
	@Override
	public Conta findByIBAN(String iban) throws EntityNotFoundException {
		return super.findOne(Example.of(Conta.builder().iban(iban).build()));
	}

	@Override
	public Conta generate(TipoConta tipoConta) { 
		String numeroConta = passwordGeneratorManager.generateOnlyDigits(sizeAccountNumber, 1);
		return Conta.builder()
		.tipoConta(tipoConta)
		.numeroConta(numeroConta)
		.iban(new StringBuilder(prefixIBAN).append(numeroConta).toString())
		.build();
	}
	
	@Override
	public void depositar(String numeroConta, Double montante) {
		Conta conta = this.findByNumeroConta(numeroConta);
		conta.setSaldoDisponivel(montante + conta.getSaldoDisponivel());
		conta.setSaldoContabilistico(montante + conta.getSaldoContabilistico());
		super.save(conta);
		
	}
	
	@Override
	public void levantar(String numeroConta, Double montante) {
		Conta conta = this.findByNumeroConta(numeroConta);
		if (conta.getSaldoContabilistico() < montante) {
			throw new IllegalArgumentException("Invalid amount: " + numeroConta);
		}
		conta.setSaldoDisponivel(conta.getSaldoDisponivel() - montante);
		conta.setSaldoContabilistico(conta.getSaldoContabilistico() - montante);
		super.save(conta);
	}
	
	@Override
	public void transferenciaInterna(String ibanContaOrigem, String ibanContaDestino, Double montante) {
		Conta contaOrigem = this.findByIBAN(ibanContaOrigem);
		Conta contaDestino = this.findByIBAN(ibanContaDestino);
		if (contaOrigem.getSaldoDisponivel() < montante) {
			throw new IllegalArgumentException("Invalid amount: " + montante);
		}
		
		contaOrigem.setSaldoDisponivel(contaOrigem.getSaldoDisponivel() - montante);
		contaOrigem.setSaldoContabilistico(contaOrigem.getSaldoContabilistico() - montante);
		contaDestino.setSaldoDisponivel(contaDestino.getSaldoDisponivel() + montante);
		contaDestino.setSaldoContabilistico(contaDestino.getSaldoContabilistico() + montante);
		super.save(List.of(contaOrigem, contaDestino));
	}

	@Override
	public void createTransferenciaInterbancaria(String ibanContaOrigem, Double montante) {
		Conta contaOrigem = this.findByIBAN(ibanContaOrigem);
		if (contaOrigem.getSaldoDisponivel() < montante) {
			throw new IllegalArgumentException("Invalid amount: " + montante);
		}
		
		contaOrigem.setSaldoDisponivel(contaOrigem.getSaldoDisponivel() - montante);
		super.save(contaOrigem);
	}
	
	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_BFA_TRANSACTION, groupId = "group-bank")
	public void createTransaction(String payload) {
		log.info("========>  BFA receiveTransaction: " + payload);
		TransacaoPayload transacaoPayload;
		try {
			transacaoPayload = new ObjectMapper().readValue(payload, TransacaoPayload.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		try {
			switch (transacaoPayload.getTipoTransacao()) {
				case DEPOSITO -> this.depositar(transacaoPayload.getNumeroConta(), transacaoPayload.getMontante());
				case LEVANTAMENTO -> this.levantar(transacaoPayload.getNumeroConta(), transacaoPayload.getMontante());
				default -> throw new IllegalArgumentException("Invalid transaction type: " + transacaoPayload.getTipoTransacao());
			}
			kafkaProducerService.send(StatusTransacaoPayload.builder()
			.status(Status.CONCLUIDO)
			.numeroOrdem(transacaoPayload.getNumeroOrdem())
			.tipoTransacao(transacaoPayload.getTipoTransacao())
			.build());
		} catch (Exception e) {
			kafkaProducerService.send(StatusTransacaoPayload.builder()
			.status(Status.NEGADO)
			.numeroOrdem(transacaoPayload.getNumeroOrdem())
			.tipoTransacao(transacaoPayload.getTipoTransacao())
			.build());
		}
	}

	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_BFA_TRANSFER, groupId = "group-bank")
	public void createTransfer(String payload) {
		log.info("========>  BFA receiveTransfer JSON: " + payload);
		TransferenciaPayload transferenciaPayload;
		try {
			transferenciaPayload = new ObjectMapper().readValue(payload, TransferenciaPayload.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		try {
			switch (transferenciaPayload.getTipoTransferencia()) {
				case INTERNA -> {
					this.transferenciaInterna(transferenciaPayload.getIbanContaOrigem(), transferenciaPayload.getIbanContaDestino(), transferenciaPayload.getMontante());
					kafkaProducerService.send(StatusTransferenciaPayload.builder()
					.status(Status.CONCLUIDO)
					.numeroOrdem(transferenciaPayload.getNumeroOrdem())
					.ibanContaOrigem(transferenciaPayload.getIbanContaOrigem())
					.ibanContaDestino(transferenciaPayload.getIbanContaDestino())
					.tipoTransferencia(transferenciaPayload.getTipoTransferencia())
					.build());
				}case INTERBANCARIA -> {
					this.createTransferenciaInterbancaria(transferenciaPayload.getIbanContaOrigem(), transferenciaPayload.getMontante());
					kafkaProducerService.send(TransferenciaInterbancariaPayload.builder()
					.montante(transferenciaPayload.getMontante())
					.numeroOrdem(transferenciaPayload.getNumeroOrdem())
					.ibanContaOrigem(transferenciaPayload.getIbanContaOrigem())
					.ibanContaDestino(transferenciaPayload.getIbanContaDestino())
					.build());
				}
				default -> throw new IllegalArgumentException("Invalid transaction type: " + transferenciaPayload.getTipoTransferencia());
			}
		} catch (Exception e) {
			kafkaProducerService.send(StatusTransferenciaPayload.builder()
			.status(Status.NEGADO)
			.numeroOrdem(transferenciaPayload.getNumeroOrdem())
			.ibanContaOrigem(transferenciaPayload.getIbanContaOrigem())
			.ibanContaDestino(transferenciaPayload.getIbanContaDestino())
			.tipoTransferencia(transferenciaPayload.getTipoTransferencia())
			.build());
		}
	}
	
	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_BFA_INTERBANK_TRANSFER, groupId = "group-bank")
	public void receiveIntebankTransfer(String payload) {
		log.info("========>  BFA receiveIntebankTransfer: " + payload);
		TransferenciaInterbancariaPayload transferenciaInterbancariaPayload;
		try {
			transferenciaInterbancariaPayload = new ObjectMapper().readValue(payload, TransferenciaInterbancariaPayload.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			throw new IllegalArgumentException(e);
		}
		try {			
			Conta contaDestino = this.findByIBAN(transferenciaInterbancariaPayload.getIbanContaDestino());
			contaDestino.setSaldoDisponivel(contaDestino.getSaldoDisponivel() + transferenciaInterbancariaPayload.getMontante());
			contaDestino.setSaldoContabilistico(contaDestino.getSaldoContabilistico() + transferenciaInterbancariaPayload.getMontante());
			super.save(contaDestino);
			kafkaProducerService.send(StatusTransferenciaInterbancariaPayload.builder()
			.status(Status.CONCLUIDO)
			.montante(transferenciaInterbancariaPayload.getMontante())
			.numeroOrdem(transferenciaInterbancariaPayload.getNumeroOrdem())
			.ibanContaOrigem(transferenciaInterbancariaPayload.getIbanContaOrigem())
			.ibanContaDestino(transferenciaInterbancariaPayload.getIbanContaDestino())
			.build());
		} catch (Exception e) {
			e.printStackTrace();
			kafkaProducerService.send(StatusTransferenciaInterbancariaPayload.builder()
			.status(Status.NEGADO)
			.montante(transferenciaInterbancariaPayload.getMontante())
			.numeroOrdem(transferenciaInterbancariaPayload.getNumeroOrdem())
			.ibanContaOrigem(transferenciaInterbancariaPayload.getIbanContaOrigem())
			.ibanContaDestino(transferenciaInterbancariaPayload.getIbanContaDestino())
			.build());
		}
	}
	
	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_BFA_STATUS_INTERBANK_TRANSFER, groupId = "group-bank")
	public void receiveStatusIntarbankTransfer(String payload) {
		StatusTransferenciaInterbancariaPayload statusTransferenciaInterbancariaPayload;
		try {
			statusTransferenciaInterbancariaPayload = new ObjectMapper().readValue(payload, StatusTransferenciaInterbancariaPayload.class);
			Conta contaOrigem = this.findByIBAN(statusTransferenciaInterbancariaPayload.getIbanContaOrigem());
			if (statusTransferenciaInterbancariaPayload.getStatus().equals(Status.CONCLUIDO)) {
				contaOrigem.setSaldoContabilistico(contaOrigem.getSaldoContabilistico() - statusTransferenciaInterbancariaPayload.getMontante());
			} else {
				contaOrigem.setSaldoDisponivel(contaOrigem.getSaldoDisponivel() + statusTransferenciaInterbancariaPayload.getMontante());
			}
			super.save(contaOrigem);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
