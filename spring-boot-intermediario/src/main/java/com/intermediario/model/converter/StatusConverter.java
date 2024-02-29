package com.intermediario.model.converter;

import com.intermediario.model.Status;

import jakarta.persistence.AttributeConverter;

public class StatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status status) {
		return status.getDescricao();
	}

	@Override
	public Status convertToEntityAttribute(String dbData) {
		return Status.of(dbData);
	}

}
