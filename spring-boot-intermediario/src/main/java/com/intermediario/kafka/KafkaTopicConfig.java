package com.intermediario.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
	public static final String TOPIC_INTERMEDIARIO_HISTORY = "topic-intermediario-history";
	public static final String TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER = "topic-intermediario-interbank-transfer";
	public static final String TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER = "topic-intermediario-status-interbank-transfer";
	
	public static final String TOPIC_BAI_TRANSACAO_HISTORY = "topic-bai-transacao-history";
	public static final String TOPIC_BFA_TRANSACAO_HISTORY = "topic-bfa-transacao-history";

	public static final String TOPIC_BAI_STATUS_INTERBANK_TRANSFER = "topic-bai-status-interbank-transfer";
	public static final String TOPIC_BAI_TRANSACAO_STATUS_TRANSFER = "topic-bai-transacao-status-transfer";
	public static final String TOPIC_BAI_TRANSACAO_INTERBANK_TRANSFER = "topic-bai-transacao-interbank-transfer";

	public static final String TOPIC_BFA_STATUS_INTERBANK_TRANSFER = "topic-bfa-status-interbank-transfer";
	public static final String TOPIC_BFA_TRANSACAO_STATUS_TRANSFER = "topic-bfa-transacao-status-transfer";
	public static final String TOPIC_BFA_TRANSACAO_INTERBANK_TRANSFER = "topic-bfa-transacao-interbank-transfer";
	
	@Bean
	NewTopic topicHistory() {
		return TopicBuilder
		.name(TOPIC_INTERMEDIARIO_HISTORY)
		.build();
	}
	
	@Bean
	NewTopic topicInterbankTransfer() {
		return TopicBuilder
		.name(TOPIC_INTERMEDIARIO_INTERBANK_TRANSFER)
		.build();
	}

	@Bean
	NewTopic topicStatusInterbankTransfer() {
		return TopicBuilder
		.name(TOPIC_INTERMEDIARIO_STATUS_INTERBANK_TRANSFER)
		.build();
	}
	
}
