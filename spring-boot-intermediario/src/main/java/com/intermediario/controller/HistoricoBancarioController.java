package com.intermediario.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intermediario.model.HistoricoBancario;
import com.intermediario.service.HistoricoBancarioService;
import com.intermediario.util.BaseController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/historicosBancarios")
public class HistoricoBancarioController extends BaseController {
	private final HistoricoBancarioService historicoBancarioService;
	
	@GetMapping
	public ResponseEntity<List<HistoricoBancario>> findAll() {
		return super.ok(historicoBancarioService.findAll());
	}
	
	@GetMapping("/{idHistoricoBancario}")
	public ResponseEntity<HistoricoBancario> findById(@PathVariable UUID idHistoricoBancario) {
		return super.ok(historicoBancarioService.findById(idHistoricoBancario));
	}

	@PostMapping("/{iban}")
	public ResponseEntity<HistoricoBancario> create(@PathVariable String iban) {
		return super.created(historicoBancarioService.create(iban));
	}
	
}
