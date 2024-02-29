package com.intermediario.kafka;

import com.intermediario.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransferenciaPayload {
	private Status status;
	private String numeroOrdem;
	private String ibanContaOrigem;
	private String ibanContaDestino;
}
