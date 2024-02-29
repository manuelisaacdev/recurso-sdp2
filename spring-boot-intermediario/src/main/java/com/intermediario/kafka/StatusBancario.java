package com.intermediario.kafka;

import java.util.List;

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
public class StatusBancario {
	private String iban;
	private List<Deposito> depositos;
	private List<Levantamento> levantamentos;
	private List<Transferencia> transferencias;
}
