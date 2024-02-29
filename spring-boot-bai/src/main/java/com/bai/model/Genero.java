package com.bai.model;

import com.bai.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum Genero {
	MASCULINO("Masculino"), FEMININO("Feminino");
	
	@Getter
	@JsonValue
	private final String descricao;
	
	public static Genero of(String descricao) {
		for (Genero genero : Genero.values()) {
			if (genero.descricao.equalsIgnoreCase(descricao)) {
				return genero;
			}
		}
		throw new BadRequestException("Gênero inválido: " + descricao);
	}
}
