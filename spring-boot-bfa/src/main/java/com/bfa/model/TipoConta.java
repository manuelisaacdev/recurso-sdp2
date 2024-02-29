package com.bfa.model;

import com.bfa.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum TipoConta {
	PESSOAL("Pessoal"), EMPRESARIAL("Empresarial");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static TipoConta of(String descricao) {
		for (TipoConta tipoConta : TipoConta.values()) {
			if (tipoConta.descricao.equalsIgnoreCase(descricao)) {
				return tipoConta;
			}
		}
		throw new BadRequestException("Tipo de conta inválida: " + descricao);
	}
}
