package com.bai.kafka;

import com.bai.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransferenciaInterbancariaPayload {
	private Status status;
	private Double montante;
	private String numeroOrdem;
	private String ibanContaOrigem;
	private String ibanContaDestino;
}
