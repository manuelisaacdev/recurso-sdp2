package com.bfa.controller;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bfa.model.Conta;
import com.bfa.service.ContaService;
import com.bfa.util.BaseController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contas")
@Secured({"GERENTE_DE_AGENCIA", "GERENTE_DE_CONTA", "ATENDENTE_DE_CAIXA"})
public class ContaController extends BaseController {
	private final ContaService contaService;

	@GetMapping
	public ResponseEntity<List<Conta>> findAll(
			@RequestParam(required = false) String numeroConta, @RequestParam(required = false) String iban,
			@RequestParam(defaultValue = "dataCriacao") String orderBy, @RequestParam(defaultValue = "DESC") Direction direction) {
		return super.ok(contaService.findAll(
		Example.of(
			Conta.builder().numeroConta(numeroConta).iban(iban).build(),
			ExampleMatcher.matching()
			.withMatcher("iban", matcher -> matcher.contains().ignoreCase())
			.withMatcher("numeroConta", matcher -> matcher.contains().ignoreCase())
		), orderBy, direction));
	}

	@GetMapping("/numeroConta/{numeroConta}")
	public ResponseEntity<Conta> findByNumeroConta(@PathVariable String numeroConta) {
		return super.ok(contaService.findOne(Example.of(Conta.builder().numeroConta(numeroConta).build())));
	}

	@GetMapping("/iban/{iban}")
	public ResponseEntity<Conta> findByIBAN(@PathVariable String iban) {
		return super.ok(contaService.findOne(Example.of(Conta.builder().iban(iban).build())));
	}
	
	@GetMapping("/contador")
	public ResponseEntity<Long> count() {
		return super.ok(contaService.count());
	}
	
	public ResponseEntity<Page<Conta>> pagination(
			@RequestParam int page, @RequestParam int size, 
			@RequestParam(required = false) String numeroConta, @RequestParam(required = false) String iban,
			@RequestParam(defaultValue = "dataCriacao") String orderBy, @RequestParam(defaultValue = "DESC") Direction direction) {
		return super.ok(contaService.pagination(page, size, Example.of(
			Conta.builder().numeroConta(numeroConta).iban(iban).build(),
			ExampleMatcher.matching()
			.withMatcher("iban", matcher -> matcher.contains().ignoreCase())
			.withMatcher("numeroConta", matcher -> matcher.contains().ignoreCase())
		), orderBy, direction));
	}
}
