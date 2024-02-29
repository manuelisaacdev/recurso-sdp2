package com.bfa.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void send(StatusTransacaoPayload payload) {
		log.info("==================> BFA SEND StatusTransacaoPayload: " + payload);
		try {
			kafkaTemplate.send(KafkaTopicConfig.TOPIC_BFA_STATUS_TRANSACTION, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(StatusTransferenciaPayload payload) {
		log.info("==================> BFA SEND TransacaoTransferenciaPayload: " + payload);
		try {
			kafkaTemplate.send(KafkaTopicConfig.TOPIC_BFA_STATUS_TRANSFER, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(TransferenciaInterbancariaPayload payload) {
		log.info("==================> BFA SEND TransferenciaInterbancariaPayload: " + payload);
		try {
			kafkaTemplate.send(KafkaTopicConfig.TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void send(StatusTransferenciaInterbancariaPayload payload) {
		log.info("==================> BFA SEND StatusTransferenciaInterbancariaPayload: " + payload);
		try {
			this.send(StatusTransferenciaPayload.builder()
			.status(payload.getStatus())
			.numeroOrdem(payload.getNumeroOrdem())
			.ibanContaOrigem(payload.getIbanContaOrigem())
			.ibanContaDestino(payload.getIbanContaDestino())
			.tipoTransferencia(TipoTransferencia.INTERBANCARIA)
			.build());
			kafkaTemplate.send(KafkaTopicConfig.TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
