package com.intermediario.kafka;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intermediario.model.HistoricoBancario;
import com.intermediario.model.Status;
import com.intermediario.repository.DepositoRepository;
import com.intermediario.repository.LevantamentoRepository;
import com.intermediario.repository.TransferenciaRepository;
import com.intermediario.service.HistoricoBancarioService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KafkaConsumerServiceImpl implements KafkaConsumerService {
	private final String baiPrefixIban;
	private final String bfaPrefixIban;
	private final DepositoRepository depositoRepository;
	private final KafkaProducerService kafkaProducerService;
	private final LevantamentoRepository levantamentoRepository;
	private final TransferenciaRepository transferenciaRepository;
	private final HistoricoBancarioService historicoBancarioService;

	public KafkaConsumerServiceImpl(@Value("${application.bai.prefix-iban}") String baiPrefixIban, 
			@Value("${application.bfa.prefix-iban}") String bfaPrefixIban, DepositoRepository depositoRepository,
			KafkaProducerService kafkaProducerService, LevantamentoRepository levantamentoRepository,
			TransferenciaRepository transferenciaRepository, HistoricoBancarioService historicoBancarioService) {
		super();
		this.baiPrefixIban = baiPrefixIban;
		this.bfaPrefixIban = bfaPrefixIban;
		this.depositoRepository = depositoRepository;
		this.kafkaProducerService = kafkaProducerService;
		this.levantamentoRepository = levantamentoRepository;
		this.transferenciaRepository = transferenciaRepository;
		this.historicoBancarioService = historicoBancarioService;
	}
	
	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_INTERMEDIARIO_HISTORY, groupId = "group-bank")
	public void receiveHistory(String payload) {
		System.out.println("receiveHistory: " + payload);
		try {
			RespostaHistoricoBancario respostaHistoricoBancario = new ObjectMapper().readValue(payload, RespostaHistoricoBancario.class);
			HistoricoBancario historicoBancario = historicoBancarioService.findById(respostaHistoricoBancario.getId());
			historicoBancario.setStatus(Status.CONCLUIDO);
			historicoBancario.setDataResposta(LocalDateTime.now());
			historicoBancarioService.save(historicoBancario);
			depositoRepository.saveAll(respostaHistoricoBancario.getDepositos().stream().map(deposito -> {
				deposito.setHistoricoBancario(historicoBancario);
				return deposito;
			}).toList());
			levantamentoRepository.saveAll(respostaHistoricoBancario.getLevantamentos().stream().map(levantamento -> {
				levantamento.setHistoricoBancario(historicoBancario);
				return levantamento;
			}).toList());
			transferenciaRepository.saveAll(respostaHistoricoBancario.getTransferencias().stream().map(transferencia -> {
				transferencia.setHistoricoBancario(historicoBancario);
				return transferencia;
			}).toList());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER, groupId = "group-bank")
	public void interbankTransfer(String payload) {
		log.info("========> INTERMEDIARIO receiveInterbankTransfer: " + payload);
		try {
			TransferenciaInterbancariaPayload transferenciaInterbancariaPayload = new ObjectMapper().readValue(payload, TransferenciaInterbancariaPayload.class);
			if (transferenciaInterbancariaPayload.getIbanContaDestino().startsWith(baiPrefixIban)) {
				kafkaProducerService.send(KafkaTopicConfig.TOPIC_BAI_TRANSACAO_INTERBANK_TRANSFER, transferenciaInterbancariaPayload);
			} else if (transferenciaInterbancariaPayload.getIbanContaDestino().startsWith(bfaPrefixIban)) {
				kafkaProducerService.send(KafkaTopicConfig.TOPIC_BFA_TRANSACAO_INTERBANK_TRANSFER, transferenciaInterbancariaPayload);				
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	@KafkaListener(topics = KafkaTopicConfig.TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER, groupId = "group-bank")
	public void statusInterbankTransfer(String payload) {
		log.info("========> INTERMEDIARIO receiveInterbankTransfer: " + payload);
		try {
			StatusTransferenciaInterbancariaPayload transferenciaInterbancariaPayload = new ObjectMapper().readValue(payload, StatusTransferenciaInterbancariaPayload.class);
			if (transferenciaInterbancariaPayload.getIbanContaOrigem().startsWith(baiPrefixIban)) {
				kafkaProducerService.send(KafkaTopicConfig.TOPIC_BAI_STATUS_INTERBANK_TRANSFER, KafkaTopicConfig.TOPIC_BAI_TRANSACAO_STATUS_TRANSFER, transferenciaInterbancariaPayload);
			} else if (transferenciaInterbancariaPayload.getIbanContaOrigem().startsWith(bfaPrefixIban)) {
				kafkaProducerService.send(KafkaTopicConfig.TOPIC_BFA_STATUS_INTERBANK_TRANSFER, KafkaTopicConfig.TOPIC_BFA_TRANSACAO_STATUS_TRANSFER, transferenciaInterbancariaPayload);				
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
