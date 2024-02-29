package com.intermediario.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intermediario.exception.InternalServerErrorRequestException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {
	private final KafkaTemplate<String, String> kafkaTemplate;

	@Override
	public void send(TransferenciaPayload payload) {
		log.info("========> sendInterbankTransfer SEND: " + payload);
//		try {
//			kafkaTemplate.send(KafkaTopicConfig.Tr, partition, null, new ObjectMapper().writeValueAsString(payload));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	public void send(StatusTransferenciaPayload payload) {
		// TODO Auto-generated method stub
	}

	@Override
	public void send(String topic, SolicitacaoHistoricoBancario payload) {
		System.out.println("========> INTERMEDIARIO SEND SolicitacaoHistoricoBancario: " + payload);
		try {
			kafkaTemplate.send(topic, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			throw new InternalServerErrorRequestException(e);
		}
	}
	
	@Override
	public void send(String topic, TransferenciaInterbancariaPayload payload) {
		System.out.println("========> INTERMEDIARIO SEND TransferenciaInterbancariaPayload: " + payload);
		try {
			kafkaTemplate.send(topic, new ObjectMapper().writeValueAsString(payload));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			throw new InternalServerErrorRequestException(e);
		}
	}
	
	@Override
	public void send(String topicBank, String topicBankTransation, StatusTransferenciaInterbancariaPayload payload) {
		System.out.println("========> INTERMEDIARIO SEND StatusTransferenciaInterbancariaPayload: " + payload);
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			kafkaTemplate.send(topicBank, objectMapper.writeValueAsString(payload));
			kafkaTemplate.send(topicBankTransation, objectMapper.writeValueAsString(StatusTransferenciaPayload.builder()
			.status(payload.getStatus())
			.numeroOrdem(payload.getNumeroOrdem())
			.ibanContaOrigem(payload.getIbanContaOrigem())
			.ibanContaDestino(payload.getIbanContaDestino())
			.build()));
		} catch (JsonProcessingException e) {
			log.error(e.getMessage(), e);
			throw new InternalServerErrorRequestException(e);
		}
	}

}
