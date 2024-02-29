package com.intermediario.model.converter;

import com.intermediario.model.Genero;

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
