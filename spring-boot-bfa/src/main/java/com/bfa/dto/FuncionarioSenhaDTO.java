package com.bfa.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioSenhaDTO {
	@NotBlank(message = "{Funcionario.senha.notblank}")
	@Size(min = 8, max = 16, message = "{Funcionario.senha.size}")
	private String nova;
	
	@NotBlank(message = "{Funcionario.senha.notblank}")
	@Size(min = 8, max = 16, message = "{Funcionario.senha.size}")
	private String antiga;
}
