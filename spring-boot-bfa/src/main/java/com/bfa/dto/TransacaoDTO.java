package com.bfa.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TransacaoDTO {
	@NotNull(message = "{Transacao.montante.notnull}")
	@Min(value = 1, message = "Transacao.montante.min")
	private Double montante;
}
