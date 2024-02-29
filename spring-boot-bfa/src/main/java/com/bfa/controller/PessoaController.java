package com.bfa.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bfa.dto.PessoaDTO;
import com.bfa.dto.PessoaUpdateDTO;
import com.bfa.model.Genero;
import com.bfa.model.Pais;
import com.bfa.model.Pessoa;
import com.bfa.model.Telefone;
import com.bfa.service.PessoaService;
import com.bfa.util.BaseController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pessoas")
@Secured({"GERENTE_DE_AGENCIA", "GERENTE_DE_CONTA", "ATENDENTE_DE_CAIXA"})
public class PessoaController extends BaseController {
	private final PessoaService pessoaService;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> findAll(
			@RequestParam(required = false) UUID pais,
			@RequestParam(required = false) String nome, 
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) Genero genero, 
			@RequestParam(required = false) String morada, 
			@RequestParam(required = false) String bilheteIdentidade,
			@RequestParam(required = false) LocalDate dataNascimento, 
			@RequestParam(defaultValue = "nome") String orderBy, 
			@RequestParam(defaultValue = "ASC") Direction direction) {
		return super.ok(pessoaService.findAll(
			Example.of(
				Pessoa.builder()
				.nome(nome)
				.email(email)
				.genero(genero)
				.morada(morada)
				.dataNascimento(dataNascimento)
				.bilheteIdentidade(bilheteIdentidade)
				.pais(Pais.builder().id(pais).build())
				.build(),
				ExampleMatcher.matching()
				.withMatcher("nome", matcher -> matcher.contains().ignoreCase())
				.withMatcher("email", matcher -> matcher.contains().ignoreCase())
			), 
			orderBy, direction
		));
	}

	@GetMapping("/{idFuncionario}")
	public ResponseEntity<Pessoa> findById(@PathVariable UUID idFuncionario) {
		return super.ok(pessoaService.findById(idFuncionario));
	}
	
	@GetMapping("/contador")
	public ResponseEntity<Long> count() {
		return super.ok(pessoaService.count());
	}
	
	@GetMapping("/paginacao")
	public ResponseEntity<Page<Pessoa>> pagination(
			@RequestParam int page, @RequestParam int size,
			@RequestParam(required = false) UUID pais,
			@RequestParam(required = false) String nome, 
			@RequestParam(required = false) String email, 
			@RequestParam(required = false) Genero genero, 
			@RequestParam(required = false) String morada, 
			@RequestParam(required = false) String bilheteIdentidade,
			@RequestParam(required = false) LocalDate dataNascimento, 
			@RequestParam(defaultValue = "nome") String orderBy, 
			@RequestParam(defaultValue = "ASC") Direction direction) {
		return super.ok(pessoaService.pagination(
				page, size,
			Example.of(
				Pessoa.builder()
				.nome(nome)
				.email(email)
				.genero(genero)
				.morada(morada)
				.dataNascimento(dataNascimento)
				.bilheteIdentidade(bilheteIdentidade)
				.pais(Pais.builder().id(pais).build())
				.build(),
				ExampleMatcher.matching()
				.withMatcher("nome", matcher -> matcher.contains().ignoreCase())
				.withMatcher("email", matcher -> matcher.contains().ignoreCase())
				.withMatcher("bilheteIdentidade", matcher -> matcher.contains().ignoreCase())
			), 
			orderBy, direction
		));
	}
	
	@PostMapping("/{idFuncionario}/{idPais}")
	public ResponseEntity<Pessoa> create(@PathVariable UUID idFuncionario, @PathVariable UUID idPais, @Valid PessoaDTO pessoaDTO, @RequestParam Optional<MultipartFile> fotoPerfil) {
		Pessoa pessoa = Pessoa.builder().build();
		BeanUtils.copyProperties(pessoaDTO, pessoa);
		pessoa.setTelefone(Telefone.builder().numero(pessoaDTO.getNumero()).build());
		return super.created(pessoaService.create(idFuncionario, idPais, pessoa, fotoPerfil));
	}
	
	@PostMapping("/lista")
	public ResponseEntity<List<Pessoa>> create(@RequestBody @Valid List<PessoaDTO> pessoasDTO) {
		return super.created(pessoaService.create(pessoasDTO.stream().map(pessoaDTO -> {
			Pessoa pessoa = Pessoa.builder().build();
			BeanUtils.copyProperties(pessoaDTO, pessoa);
			pessoa.setTelefone(Telefone.builder().numero(pessoaDTO.getNumero()).build());
			return pessoa;
		}).toList()));
	}
	
	@PutMapping("/{idPessoa}/{idPais}")
	public ResponseEntity<Pessoa>  update(@PathVariable UUID idPessoa, @PathVariable UUID idPais, @RequestBody @Valid PessoaUpdateDTO pessoaUpdateDTO) {
		var pessoa = Pessoa.builder().build();
		BeanUtils.copyProperties(pessoaUpdateDTO, pessoa);
		return super.ok(pessoaService.update(idPessoa, idPais, pessoa));
	}
	
	@PatchMapping("/fotoPerfil/{idPessoa}")
	public ResponseEntity<Pessoa> updateProfilePhoto(@PathVariable UUID idPessoa, @RequestParam MultipartFile fotoPerfil) {
		return super.ok(pessoaService.updateProfilePhoto(idPessoa, fotoPerfil));
	}

	@DeleteMapping("/{idPessoa}")
	public ResponseEntity<Pessoa>  delete(@PathVariable UUID idPessoa) {
		return super.ok(pessoaService.deleteById(idPessoa));
	}
}
