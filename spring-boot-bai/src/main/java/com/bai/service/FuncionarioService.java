package com.bai.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.bai.dto.FuncionarioEmailDTO;
import com.bai.dto.FuncionarioSenhaDTO;
import com.bai.exception.EntityNotFoundException;
import com.bai.exception.UnauthorizedException;
import com.bai.model.Funcionario;
import com.bai.model.Telefone;

public interface FuncionarioService extends AbstractService<Funcionario, UUID> {
	public Funcionario findByEmail(String email) throws EntityNotFoundException;
	public List<Funcionario> create(List<Funcionario> funcionarios);
	public Funcionario update(UUID idFuncionario, UUID idPais, Funcionario funcionario);
	public Funcionario updateProfilePhoto(UUID idFuncionario, MultipartFile fotoPerfil);
	public Funcionario updatePassword(UUID idFuncionario, FuncionarioSenhaDTO funcionarioSenhaDTO) throws UnauthorizedException;
	public Funcionario updateEmail(UUID idUsuario, FuncionarioEmailDTO updateEmailDTO) throws UnauthorizedException;
	public Funcionario create(UUID idPais, Funcionario funcionario, Telefone telefone, Optional<MultipartFile> fotoPerfil);
}
