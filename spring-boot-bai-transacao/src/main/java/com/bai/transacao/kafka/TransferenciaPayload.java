package com.bai.transacao.kafka;

import com.bai.transacao.model.TipoTransferencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenciaPayload {
	private Double montante;
	private String numeroOrdem;
	private String ibanContaOrigem;
	private String ibanContaDestino;
	private TipoTransferencia tipoTransferencia;
}
