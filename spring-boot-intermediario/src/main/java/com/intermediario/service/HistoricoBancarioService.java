package com.intermediario.service;

import java.util.UUID;

import com.intermediario.model.HistoricoBancario;

public interface HistoricoBancarioService extends AbstractService<HistoricoBancario, UUID> {
	public HistoricoBancario create(String iban);
}
