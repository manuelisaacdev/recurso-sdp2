package com.bai.transacao.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;

import com.bai.transacao.exception.BadRequestException;
import com.bai.transacao.model.Status;
import com.bai.transacao.model.Transferencia;

public interface TransferenciaService extends AbstractService<Transferencia, UUID> {
	public List<Transferencia> findAllByIBAN(String iban, String orderBy, Direction direction) throws BadRequestException;
	public Page<Transferencia> paginationByIBAN(int page, int size, String iban, String orderBy, Direction direction) throws BadRequestException;
	public Transferencia create(Transferencia transferencia);
	public void updateStatus(String numeroOrdem, Status status);
}
