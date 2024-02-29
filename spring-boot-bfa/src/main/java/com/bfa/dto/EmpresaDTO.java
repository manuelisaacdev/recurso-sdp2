package com.bfa.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EmpresaDTO extends TelefoneDTO {
	@NotBlank(message = "{Empresa.nome.notblank}")
	private String nome;	
}
