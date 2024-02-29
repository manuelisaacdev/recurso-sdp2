package com.bfa.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bfa.model.Pais;
import com.bfa.model.Pessoa;
import com.bfa.model.TipoConta;
import com.bfa.repository.PessoaRepository;
import com.bfa.service.AbstractService;
import com.bfa.service.ContaService;
import com.bfa.service.FuncionarioService;
import com.bfa.service.PessoaService;
import com.bfa.storage.StorageService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PessoaServiceImpl extends AbstractServiceImpl<Pessoa, UUID, PessoaRepository> implements PessoaService {
	private final ContaService contaService;
	private final StorageService storageService;
	private final FuncionarioService funcionarioService;
	private final AbstractService<Pais, UUID> paisService;

	public PessoaServiceImpl(PessoaRepository repository, HttpServletRequest request, MessageSource messageSource,
			ContaService contaService, StorageService storageService, FuncionarioService funcionarioService,
			AbstractService<Pais, UUID> paisService) {
		super(repository, request, messageSource);
		this.contaService = contaService;
		this.storageService = storageService;
		this.funcionarioService = funcionarioService;
		this.paisService = paisService;
	}

	@Override
	public Pessoa create(UUID idFuncionario, UUID idPais, Pessoa pessoa, Optional<MultipartFile> fotoPerfil) {
		pessoa.setPais(paisService.findById(idPais));
		pessoa.setConta(contaService.generate(TipoConta.PESSOAL));
		pessoa.setFuncionario(funcionarioService.findById(idFuncionario));
		if (fotoPerfil.isPresent()) {
			pessoa.setFotoPerfil(storageService.store(fotoPerfil.get()));
		}
		return super.save(pessoa);
	}
	
	@Override
	public List<Pessoa> create(List<Pessoa> pessoas) {
		SecureRandom random = new SecureRandom();
		var paises = paisService.findAll();
		var funcionarios = funcionarioService.findAll();
		return super.save(pessoas.stream().map(pessoa -> {
			pessoa.setPais(paises.get(random.nextInt(paises.size() - 1)));
			pessoa.setConta(contaService.generate(TipoConta.PESSOAL));
			pessoa.setFuncionario(funcionarios.get(random.nextInt(funcionarios.size() - 1)));
			return pessoa;
		}).toList());
	}

	@Override
	public Pessoa update(UUID idPessoa, UUID idPais, Pessoa pessoa) {
		Pessoa entity = super.findById(idPessoa);
		entity.setNome(pessoa.getNome());
		entity.setGenero(pessoa.getGenero());
		entity.setMorada(pessoa.getMorada());
		entity.setPais(paisService.findById(idPais));
		entity.setDataNascimento(pessoa.getDataNascimento());
		entity.setBilheteIdentidade(pessoa.getBilheteIdentidade());
		return entity;
	}

	@Override
	public Pessoa updateProfilePhoto(UUID idPessoa, MultipartFile fotoPerfil) {
		Pessoa entity = this.findById(idPessoa);
		String arquivo = storageService.store(fotoPerfil);
		storageService.delete(entity.getFotoPerfil());
		entity.setFotoPerfil(arquivo);
		return super.save(entity);
	}
	
	@Override
	public Pessoa deleteById(UUID id) {
		Pessoa entity = super.deleteById(id);
		storageService.delete(entity.getFotoPerfil());
		return entity;	
	}

}
