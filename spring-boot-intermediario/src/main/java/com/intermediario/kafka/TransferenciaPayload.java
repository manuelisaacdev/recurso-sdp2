package com.intermediario.kafka;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaPayload {
	private String numeroOrdem;
	private Double montante;
	private String ibanContaOrigem;
	private String ibanContaDestino;
}
