package com.intermediario.kafka;

public interface KafkaConsumerService {
	public void interbankTransfer(String payload);
	public void statusInterbankTransfer(String payload);
	public void receiveHistory(String payload);
}
