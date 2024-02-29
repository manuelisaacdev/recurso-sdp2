package com.bfa.dto;

import java.time.LocalDate;

import com.bfa.model.Genero;
import com.bfa.model.Papel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FuncionarioDTO extends TelefoneDTO {
	@NotBlank(message = "{Funcionario.nome.notblank}")
	private String nome;

	@NotNull(message = "{Funcionario.genero.notnull}")
	private Genero genero;
	
	@NotNull(message = "{Funcionario.dataNascimento.notnull}")
	private LocalDate dataNascimento;

	@Email(message = "{Funcionario.email.email}")
	@NotBlank(message = "{Funcionario.email.notblank}")
	private String email;

	@NotBlank(message = "{Funcionario.bilheteIdentidade.notblank}")
	private String bilheteIdentidade;

	@NotBlank(message = "{Funcionario.morada.notblank}")
	@Pattern(message = "{Funcionario.morada.pattern}", regexp = "Rua \\d+ - Casa \\d+ - .+")
	private String morada;

	@NotNull(message = "{Funcionario.papel.notnull}")
	private Papel papel;
	
	@NotBlank(message = "{Funcionario.senha.notblank}")
	@Size(min = 8, max = 16, message = "{Funcionario.senha.size}")
	private String senha;
}
