package com.bfa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmpresaUpdateDTO {
	@NotBlank(message = "{Empresa.nome.notblank}")
	private String nome;
	
}
