package com.bfa.kafka;

public interface KafkaProducerService {
	public void send(StatusTransacaoPayload payload);
	public void send(StatusTransferenciaPayload payload);
	public void send(TransferenciaInterbancariaPayload payload);
	public void send(StatusTransferenciaInterbancariaPayload payload);
}
