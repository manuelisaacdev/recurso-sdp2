package com.bai.service;

import com.bai.model.Funcionario;
import com.bai.model.Token;

public interface TokenService extends AbstractService<Token, String> {
	public Token refresh(String authorization);
	public Token update(String id, Token token);
	public Token create(Funcionario funcionario);
	public void invalidateAll(Funcionario funcionario);	
}
