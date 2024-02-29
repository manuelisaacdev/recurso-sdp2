package com.bfa.dto;

import java.time.LocalDate;

import com.bfa.model.Genero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FuncionarioUpdateDTO {
	@NotBlank(message = "{Funcionario.nome.notblank}")
	private String nome;

	@NotNull(message = "{Funcionario.genero.notnull}")
	private Genero genero;
	
	@NotNull(message = "{Funcionario.dataNascimento.notnull}")
	private LocalDate dataNascimento;

	@NotBlank(message = "{Funcionario.morada.notblank}")
	@Pattern(message = "{Funcionario.morada.pattern}", regexp = "Rua \\d+ - Casa \\d+ - .+")
	private String morada;

	@NotBlank(message = "{Funcionario.bilheteIdentidade.notblank}")
	private String bilheteIdentidade;
}
