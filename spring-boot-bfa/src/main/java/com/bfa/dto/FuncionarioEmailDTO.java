package com.bfa.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioEmailDTO {
	@Email(message = "{Funcionario.email.email}")
	@NotBlank(message = "{Funcionario.email.notblank}")
	private String email;

	@NotBlank(message = "{Funcionario.senha.notblank}")
	@Size(min = 8, max = 16, message = "{Funcionario.senha.size}")
	private String senha;

}
