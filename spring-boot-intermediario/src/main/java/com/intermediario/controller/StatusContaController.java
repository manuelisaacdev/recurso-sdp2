package com.intermediario.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intermediario.service.StatusContaService;
import com.intermediario.util.BaseController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/status")
public class StatusContaController extends BaseController {
	private final StatusContaService statusContaService;
	
	@GetMapping(value = "/contas/iban/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findContaByIBAN(@PathVariable String iban) {
		return super.ok(statusContaService.findConta(iban));
	}
	
	@GetMapping(value = "/depositos/iban/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findAllDeposito(@PathVariable String iban) {
		return super.ok(statusContaService.findAllDeposito(iban));
	}
	
	@GetMapping(value = "/levantamentos/iban/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findAllLevantamento(@PathVariable String iban) {
		return super.ok(statusContaService.findAllLevantamento(iban));
	}
	
	@GetMapping(value = "/transferencias/iban/{iban}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> findAllTransferencia(@PathVariable String iban) {
		return super.ok(statusContaService.findAllTransferencia(iban));
	}

}
