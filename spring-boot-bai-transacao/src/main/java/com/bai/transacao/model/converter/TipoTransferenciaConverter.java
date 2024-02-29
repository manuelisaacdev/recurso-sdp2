package com.bai.transacao.model.converter;

import com.bai.transacao.model.TipoTransferencia;

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
