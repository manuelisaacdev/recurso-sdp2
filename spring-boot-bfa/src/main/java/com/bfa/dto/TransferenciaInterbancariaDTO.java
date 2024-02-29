package com.bfa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransferenciaInterbancariaDTO {
	@NotNull(message = "{Transacao.montante.notnull}")
	@Min(value = 1, message = "Transacao.montante.min")
	private Double montante;

	@NotBlank(message = "{TransferenciaInterbancaria.ibanContaOrigem.notblank}")
	private String ibanContaOrigem;

	@NotBlank(message = "{TransferenciaInterbancaria.ibanContaDestino.notblank}")
	private String ibanContaDestino;

}
