package com.intermediario.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.intermediario.exception.BadRequestException;
import com.intermediario.kafka.KafkaProducerService;
import com.intermediario.kafka.KafkaTopicConfig;
import com.intermediario.kafka.SolicitacaoHistoricoBancario;
import com.intermediario.model.HistoricoBancario;
import com.intermediario.model.Status;
import com.intermediario.repository.HistoricoBancarioRepository;
import com.intermediario.service.HistoricoBancarioService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class HistoricoBancarioServiceImpl extends AbstractServiceImpl<HistoricoBancario, UUID, HistoricoBancarioRepository> implements HistoricoBancarioService {
	private final String baiPrefixIban;
	private final String bfaPrefixIban;
	private final KafkaProducerService kafkaProducerService;
	
	public HistoricoBancarioServiceImpl(HistoricoBancarioRepository repository, HttpServletRequest request,
			MessageSource messageSource, 
			@Value("${application.bai.prefix-iban}") String baiPrefixIban, @Value("${application.bfa.prefix-iban}") String bfaPrefixIban, 
			KafkaProducerService kafkaProducerService) {
		super(repository, request, messageSource);
		this.baiPrefixIban = baiPrefixIban;
		this.bfaPrefixIban = bfaPrefixIban;
		this.kafkaProducerService = kafkaProducerService;
	}

	@Override
	public HistoricoBancario create(String iban) {
		if (!(iban.startsWith(baiPrefixIban) || iban.startsWith(bfaPrefixIban))) {
			throw new BadRequestException("Invalid iban: " + iban);
		}
		HistoricoBancario historicoBancario = super.save(HistoricoBancario.builder()
		.iban(iban)
		.status(Status.PROCESSO)
		.build());
		
		if (iban.startsWith(baiPrefixIban)) {
			kafkaProducerService.send(
			KafkaTopicConfig.TOPIC_BAI_TRANSACAO_HISTORY, 
			SolicitacaoHistoricoBancario.builder()
			.iban(iban)
			.id(historicoBancario.getId())
			.build());
		}else if (iban.startsWith(bfaPrefixIban)) {
			kafkaProducerService.send(
			KafkaTopicConfig.TOPIC_BFA_TRANSACAO_HISTORY, 
			SolicitacaoHistoricoBancario.builder()
			.iban(iban)
			.id(historicoBancario.getId())
			.build());
		}
		return historicoBancario;
	}

}
