package com.bfa.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bfa.dto.EmpresaDTO;
import com.bfa.dto.EmpresaUpdateDTO;
import com.bfa.model.Empresa;
import com.bfa.model.Telefone;
import com.bfa.service.EmpresaService;
import com.bfa.util.BaseController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/empresas")
@Secured({"GERENTE_DE_AGENCIA", "GERENTE_DE_CONTA", "ATENDENTE_DE_CAIXA"})
public class EmpresaController extends BaseController {
	private final EmpresaService empresaService;
	
	@GetMapping
	public ResponseEntity<List<Empresa>> findAll(
			@RequestParam(required = false) String nome, 
			@RequestParam(defaultValue = "nome") String orderBy, 
			@RequestParam(defaultValue = "ASC") Direction direction) {
		return super.ok(empresaService.findAll(
			Example.of(
				Empresa.builder()
				.nome(nome)
				.build(),
				ExampleMatcher.matching()
				.withMatcher("nome", matcher -> matcher.contains().ignoreCase())
			), 
			orderBy, direction
		));
	}

	@GetMapping("/{idEmpresa}")
	public ResponseEntity<Empresa> findById(@PathVariable UUID idEmpresa) {
		return super.ok(empresaService.findById(idEmpresa));
	}
	
	@GetMapping("/contador")
	public ResponseEntity<Long> count() {
		return super.ok(empresaService.count());
	}
	
	@GetMapping("/paginacao")
	public ResponseEntity<Page<Empresa>> pagination(
			@RequestParam int page, @RequestParam int size,
			@RequestParam(required = false) String nome, 
			@RequestParam(defaultValue = "nome") String orderBy, 
			@RequestParam(defaultValue = "ASC") Direction direction) {
		return super.ok(empresaService.pagination(
				page, size,
			Example.of(
				Empresa.builder()
				.nome(nome)
				.build(),
				ExampleMatcher.matching()
				.withMatcher("nome", matcher -> matcher.contains().ignoreCase())
			), 
			orderBy, direction
		));
	}
	
	@PostMapping("/{idFuncionario}")
	public ResponseEntity<Empresa> create(@PathVariable UUID idFuncionario, @RequestBody @Valid EmpresaDTO empresaDTO) {
		Empresa empresa = Empresa.builder().build();
		BeanUtils.copyProperties(empresaDTO, empresa);
		empresa.setTelefone(Telefone.builder().numero(empresaDTO.getNumero()).build());
		return super.created(empresaService.create(idFuncionario, empresa));
	}
	
	@PostMapping("/lista")
	public ResponseEntity<List<Empresa>> create(@RequestBody @Valid List<EmpresaDTO> empresasDTO) {
		return super.created(empresaService.create(empresasDTO.stream().map(empresaDTO -> {
			Empresa empresa = Empresa.builder().build();
			BeanUtils.copyProperties(empresaDTO, empresa);
			empresa.setTelefone(Telefone.builder().numero(empresaDTO.getNumero()).build());
			return empresa;
		}).toList()));
	}
	
	@PutMapping("/{idEmpresa}")
	public ResponseEntity<Empresa>  update(@PathVariable UUID idEmpresa, @RequestBody @Valid EmpresaUpdateDTO empresaUpdateDTO) {
		Empresa empresa = Empresa.builder().build();
		BeanUtils.copyProperties(empresaUpdateDTO, empresa);
		return super.ok(empresaService.update(idEmpresa, empresa));
	}

	@DeleteMapping("/{idEmpresa}")
	public ResponseEntity<Empresa>  delete(@PathVariable UUID idEmpresa) {
		return super.ok(empresaService.deleteById(idEmpresa));
	}
}
