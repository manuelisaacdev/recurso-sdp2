package com.bai.transacao.kafka;

import com.bai.transacao.model.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransacaoPayload {
	private Status status;
	private String numeroOrdem;
	private TipoTransacao tipoTransacao;
}
