package com.intermediario.kafka;

public interface KafkaProducerService {
	public void send(TransferenciaPayload payload);
	public void send(StatusTransferenciaPayload payload);
	public void send(String topic, SolicitacaoHistoricoBancario payload);
	public void send(String topic, TransferenciaInterbancariaPayload payload);
	public void send(String topicBank, String topicBankTransation, StatusTransferenciaInterbancariaPayload payload);
}
