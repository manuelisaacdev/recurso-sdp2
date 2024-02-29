package com.bfa.dto;

import java.time.LocalDate;

import com.bfa.model.Genero;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PessoaUpdateDTO {
	@NotBlank(message = "{Pessoa.nome.notblank}")
	private String nome;
	
	@NotNull(message = "{Pessoa.nome.notnull}")
	private Genero genero;

	@NotNull(message = "{Pessoa.nome.notnull}")
	private LocalDate dataNascimento;

	@NotBlank(message = "{Pessoa.nome.notblank}")
	private String email;

	@NotBlank(message = "{Pessoa.nome.notblank}")
	private String bilheteIdentidade;

	@NotBlank(message = "{Pessoa.nome.notblank}")
	private String morada;
}
