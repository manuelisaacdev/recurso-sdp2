package com.bai.model.converter;

import com.bai.model.Papel;

import jakarta.persistence.AttributeConverter;

public class PapelConverter implements AttributeConverter<Papel, String> {

	@Override
	public String convertToDatabaseColumn(Papel papel) {
		return papel.getDescricao();
	}

	@Override
	public Papel convertToEntityAttribute(String dbData) {
		return Papel.of(dbData);
	}

}
