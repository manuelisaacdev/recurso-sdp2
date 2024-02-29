package com.bfa.service.impl;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bfa.model.Empresa;
import com.bfa.model.TipoConta;
import com.bfa.repository.EmpresaRepository;
import com.bfa.service.ContaService;
import com.bfa.service.EmpresaService;
import com.bfa.service.FuncionarioService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class EmpresaServiceImpl extends AbstractServiceImpl<Empresa, UUID, EmpresaRepository> implements EmpresaService {
	private final ContaService contaService;
	private final FuncionarioService funcionarioService;

	public EmpresaServiceImpl(EmpresaRepository repository, HttpServletRequest request, MessageSource messageSource,
			ContaService contaService, FuncionarioService funcionarioService) {
		super(repository, request, messageSource);
		this.contaService = contaService;
		this.funcionarioService = funcionarioService;
	}

	@Override
	public Empresa create(UUID idFuncionario, Empresa empresa) {
		empresa.setFuncionario(funcionarioService.findById(idFuncionario));
		empresa.setConta(contaService.generate(TipoConta.EMPRESARIAL));
		return super.save(empresa);
	}

	@Override
	public List<Empresa> create(List<Empresa> empresas) {
		SecureRandom random = new SecureRandom();
		var funcionarios = funcionarioService.findAll();
		return super.save(empresas.stream().map(empresa -> {
			empresa.setConta(contaService.generate(TipoConta.EMPRESARIAL));
			empresa.setFuncionario(funcionarios.get(random.nextInt(funcionarios.size() - 1)));
			return empresa;
		}).toList());
	}

	@Override
	public Empresa update(UUID idEmpresa, Empresa empresa) {
		Empresa entity = super.findById(idEmpresa);
		entity.setNome(empresa.getNome());
		return entity;
	}
}
