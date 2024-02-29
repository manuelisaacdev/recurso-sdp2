package com.bfa.kafka;

public interface KafkaConsumerService {
	public void createTransaction(String payload);
	
	public void createTransfer(String payload);
	public void receiveIntebankTransfer(String payload);
	public void receiveStatusIntarbankTransfer(String payload);
}
