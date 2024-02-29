package com.bai.model.converter;

import com.bai.model.Genero;

import jakarta.persistence.AttributeConverter;

public class GeneroConverter implements AttributeConverter<Genero, String> {

	@Override
	public String convertToDatabaseColumn(Genero genero) {
		return genero.getDescricao();
	}

	@Override
	public Genero convertToEntityAttribute(String dbData) {
		return Genero.of(dbData);
	}

}
