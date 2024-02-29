package com.bai.transacao.service;

import java.util.UUID;

import com.bai.transacao.exception.JWTServiceException;

public interface JWTService {
	public UUID extractSubjectAccessToken(String authorization);
	public String extractToken(String authorization) throws JWTServiceException;
}
