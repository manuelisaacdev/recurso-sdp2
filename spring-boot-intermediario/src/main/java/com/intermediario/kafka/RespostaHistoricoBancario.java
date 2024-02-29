package com.intermediario.kafka;

import java.util.List;
import java.util.UUID;

import com.intermediario.model.Deposito;
import com.intermediario.model.Levantamento;
import com.intermediario.model.Transferencia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RespostaHistoricoBancario {
	private UUID id;
	private String iban;
	private List<Deposito> depositos;
	private List<Levantamento> levantamentos;
	private List<Transferencia> transferencias;
}
