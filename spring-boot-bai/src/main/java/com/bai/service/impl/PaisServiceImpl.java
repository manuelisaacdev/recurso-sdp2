package com.bai.service.impl;

import java.util.UUID;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import com.bai.model.Pais;
import com.bai.repository.PaisRepository;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class PaisServiceImpl extends AbstractServiceImpl<Pais, UUID, PaisRepository> {

	protected PaisServiceImpl(PaisRepository repository, HttpServletRequest request, MessageSource messageSource) {
		super(repository, request, messageSource);
	}
	
}
