package com.bfa.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bfa.model.Telefone;
import com.bfa.repository.TelefoneRepository;
import com.bfa.service.FuncionarioService;
import com.bfa.service.TelefoneService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TelefoneServiceimpl extends AbstractServiceImpl<Telefone, UUID, TelefoneRepository> implements TelefoneService {
	private final FuncionarioService funcionarioService;

	public TelefoneServiceimpl(TelefoneRepository repository, HttpServletRequest request, MessageSource messageSource,
			FuncionarioService funcionarioService) {
		super(repository, request, messageSource);
		this.funcionarioService = funcionarioService;
	}
	
	@Override
	public List<Telefone> findAllByFuncionario(UUID idFuncionario) {
		return this.funcionarioService.findById(idFuncionario).getTelefones();
	}

	@Override
	public Telefone createByFuncionario(UUID idFuncionario, Telefone telefone) {
		var funcionario = this.funcionarioService.findById(idFuncionario);
		funcionario.getTelefones().add(telefone);
		funcionarioService.save(funcionario);
		return telefone;
	}

	@Override
	public Telefone deleteByFuncionario(UUID idFuncionario, UUID idTelefone) {
		var funcionario = this.funcionarioService.findById(idFuncionario);
		var telefone = super.findById(idTelefone);
		funcionario.getTelefones().remove(telefone);
		funcionarioService.save(funcionario);
		return telefone;
	}
}
