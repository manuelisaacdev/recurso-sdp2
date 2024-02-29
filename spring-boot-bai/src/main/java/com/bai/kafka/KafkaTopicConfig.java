package com.bai.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {	
	public static final String TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER = "topic-intermediario-interbank-transfer";
	public static final String TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER = "topic-intermediario-status-interbank-transfer";
	
	// Tópico que o banco receberá as transações
	public static final String TOPIC_BAI_TRANSFER = "topic-bai-transfer";
	public static final String TOPIC_BAI_TRANSACTION = "topic-bai-transaction";
	
	// Tópico que o seviço de transações receberá os status das transações
	public static final String TOPIC_BAI_STATUS_TRANSFER = "topic-bai-status-transfer";
	public static final String TOPIC_BAI_STATUS_TRANSACTION = "topic-bai-status-transaction";
	
	public static final String TOPIC_BAI_INTERBANK_TRANSFER = "topic-bai-interbank-transfer";
	public static final String TOPIC_BAI_STATUS_INTERBANK_TRANSFER = "topic-bai-status-interbank-transfer";
	
	@Bean
	NewTopic topicTransation() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSACTION)
		.build();
	}
	
	@Bean
	NewTopic topicTransfer() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSFER)
		.build();
	}
	
	@Bean
	NewTopic topicStatusTransfer() {
		return TopicBuilder
		.name(TOPIC_BAI_INTERBANK_TRANSFER)
		.build();
	}
	
	@Bean
	NewTopic topicStatusInterbankTransfer() {
		return TopicBuilder
		.name(TOPIC_BAI_STATUS_INTERBANK_TRANSFER)
		.build();
	}
	
//	@Bean
//	NewTopic topicTransaction() {
//		return TopicBuilder
//		.name(TOPIC_BAI_TRANSACTION)
//		.build();
//	}
}
