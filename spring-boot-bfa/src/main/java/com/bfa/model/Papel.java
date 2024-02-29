package com.bfa.model;

import org.springframework.security.core.GrantedAuthority;

import com.bfa.exception.BadRequestException;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@RequiredArgsConstructor
public enum Papel implements GrantedAuthority {
	GERENTE_DE_CONTA("GERENTE_DE_CONTA", "Gerente de Contas"),
	GERENTE_DE_AGENCIA("GERENTE_DE_AGENCIA", "Gerente de Agência"),
	ATENDENTE_DE_CAIXA("ATENDENTE_DE_CAIXA", "Atendente de Caixa"),
	ANALISTA_DE_CREDITO("ANALISTA_DE_CREDITO", "Analista de Crédito"),
	ANALISTA_FINANCEIRO("ANALISTA_FINANCEIRO", "Analista Financeiro"),
	CONSULTOR_FINANCEIRO("CONSULTOR_FINANCEIRO", "Consultor Financeiro");

	private final String name;

	@Getter
	@JsonValue
	private final String descricao;
	
	public static Papel of(String descricao) {
		for (Papel genero : Papel.values()) {
			if (genero.descricao.equalsIgnoreCase(descricao)) {
				return genero;
			}
		}
		throw new BadRequestException("Papel inválido: " + descricao);
	}

	@Override
	public String getAuthority() {
		return name;
	}
}
