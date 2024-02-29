package com.bai.transacao.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	// Tópico que o banco receberá as transações internas
	public static final String TOPIC_BAI_TRANSFER = "topic-bai-transfer";
	public static final String TOPIC_BAI_TRANSACTION = "topic-bai-transaction";
	
	public static final String TOPIC_INTERMEDIARIO_HISTORY = "topic-intermediario-history";
	
	// Tópico que o seviço de transações receberá os status das transações
	public static final String TOPIC_BAI_TRANSACAO_HISTORY = "topic-bai-transacao-history";
	public static final String TOPIC_BAI_TRANSACAO_STATUS_TRANSFER = "topic-bai-transacao-status-transfer";
	public static final String TOPIC_BAI_TRANSACAO_STATUS_TRANSACTION = "topic-bai-transacao-status-transaction";
	
	public static final String TOPIC_BAI_INTERBANK_TRANSFER = "topic-bai-interbank-transfer";
	public static final String TOPIC_BAI_TRANSACAO_INTERBANK_TRANSFER = "topic-bai-transacao-interbank-transfer";
	
	@Bean
	NewTopic topicReceiveStatusTransfer() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSACAO_STATUS_TRANSFER)
		.build();
	}

	@Bean
	NewTopic topicStatusTransaction() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSACAO_STATUS_TRANSACTION)
		.build();
	}

	@Bean
	NewTopic topicHistory() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSACAO_HISTORY)
		.build();
	}

	@Bean
	NewTopic topicInterbankTransfer() {
		return TopicBuilder
		.name(TOPIC_BAI_TRANSACAO_INTERBANK_TRANSFER)
		.build();
	}
}
