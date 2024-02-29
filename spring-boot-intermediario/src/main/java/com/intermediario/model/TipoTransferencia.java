package com.intermediario.model;

import com.fasterxml.jackson.annotation.JsonValue;
import com.intermediario.exception.BadRequestException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoTransferencia {
	INTERNA("Interna"), INTERBANCARIA("Interbancária");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoTransferencia of(String descricao) {
		for (TipoTransferencia tipoTransferencia : TipoTransferencia.values()) {
			if (tipoTransferencia.descricao.equalsIgnoreCase(descricao)) {
				return tipoTransferencia;
			}
		}
		throw new BadRequestException("Tipo de transferência inválida: " + descricao);
	}
}
