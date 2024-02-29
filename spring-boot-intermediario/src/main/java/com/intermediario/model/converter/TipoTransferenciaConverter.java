package com.intermediario.model.converter;

import com.intermediario.model.TipoTransferencia;

import jakarta.persistence.AttributeConverter;

public class TipoTransferenciaConverter implements AttributeConverter<TipoTransferencia, String> {

	@Override
	public String convertToDatabaseColumn(TipoTransferencia tipoTransferencia) {
		return tipoTransferencia.getDescricao();
	}

	@Override
	public TipoTransferencia convertToEntityAttribute(String dbData) {
		return TipoTransferencia.of(dbData);
	}

}
