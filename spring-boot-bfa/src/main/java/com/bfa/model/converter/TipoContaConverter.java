package com.bfa.model.converter;

import com.bfa.model.TipoConta;

import jakarta.persistence.AttributeConverter;

public class TipoContaConverter implements AttributeConverter<TipoConta, String> {

	@Override
	public String convertToDatabaseColumn(TipoConta tipoConta) {
		return tipoConta.getDescricao();
	}

	@Override
	public TipoConta convertToEntityAttribute(String dbData) {
		return TipoConta.of(dbData);
	}

}
