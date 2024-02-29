package com.bfa.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bfa.model.Pessoa;

public interface PessoaService extends AbstractService<Pessoa, UUID> {
	public Pessoa create(UUID idFuncionario, UUID idPais, Pessoa pessoa, Optional<MultipartFile> fotoPerfil);
	public List<Pessoa> create(List<Pessoa> pessoas);
	public Pessoa update(UUID idPessoa, UUID idPais, Pessoa pessoa);
	public Pessoa updateProfilePhoto(UUID idPessoa, MultipartFile fotoPerfil);
}
