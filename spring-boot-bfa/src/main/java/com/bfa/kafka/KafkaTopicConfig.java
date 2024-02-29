package com.bfa.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {	
	public static final String TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER = "topic-intermediario-interbank-transfer";
	public static final String TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER = "topic-intermediario-status-interbank-transfer";
	
	// Tópico que o banco receberá as transações
	public static final String TOPIC_BFA_TRANSFER = "topic-bfa-transfer";
	public static final String TOPIC_BFA_TRANSACTION = "topic-bfa-transaction";
	
	// Tópico que o seviço de transações receberá os status das transações
	public static final String TOPIC_BFA_STATUS_TRANSFER = "topic-bfa-status-transfer";
	public static final String TOPIC_BFA_STATUS_TRANSACTION = "topic-bfa-status-transaction";
	
	public static final String TOPIC_BFA_INTERBANK_TRANSFER = "topic-bfa-interbank-transfer";
	public static final String TOPIC_BFA_STATUS_INTERBANK_TRANSFER = "topic-bfa-status-interbank-transfer";

	@Bean
	NewTopic topicTransation() {
		return TopicBuilder
		.name(TOPIC_BFA_TRANSACTION)
		.build();
	}
	
	@Bean
	NewTopic topicTransfer() {
		return TopicBuilder
		.name(TOPIC_BFA_TRANSFER)
		.build();
	}
	
	@Bean
	NewTopic topicStatusTransfer() {
		return TopicBuilder
		.name(TOPIC_BFA_INTERBANK_TRANSFER)
		.build();
	}
	
	@Bean
	NewTopic topicStatusInterbankTransfer() {
		return TopicBuilder
		.name(TOPIC_BFA_STATUS_INTERBANK_TRANSFER)
		.build();
	}
	
//	@Bean
//	NewTopic topicTransaction() {
//		return TopicBuilder
//		.name(TOPIC_BAI_TRANSACTION)
//		.build();
//	}
}
