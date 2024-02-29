package com.bai.transacao.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.bai.transacao.exception.BadRequestException;
import com.bai.transacao.model.Levantamento;
import com.bai.transacao.model.Status;

public interface LevantamentoService extends AbstractService<Levantamento, UUID> {
	public List<Levantamento> findAllByIBAN(String iban, String orderBy, Direction direction) throws BadRequestException;
	public Page<Levantamento> paginationByIBAN(int page, int size, String iban, String orderBy, Direction direction) throws BadRequestException;
	public Levantamento create(Levantamento levantamento);
	public void updateStatus(String numeroOrdem, Status status);
}
