package com.bai.service;

import java.util.List;
import java.util.UUID;

import com.bai.model.Empresa;

public interface EmpresaService extends AbstractService<Empresa, UUID> {
	public Empresa create(UUID idFuncionario, Empresa empresa);
	public List<Empresa> create(List<Empresa> empresas);
	public Empresa update(UUID idEmpresa, Empresa empresa);
}
