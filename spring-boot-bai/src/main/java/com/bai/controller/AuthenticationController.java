package com.bai.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bai.dto.RefreshTokenDTO;
import com.bai.model.Token;
import com.bai.security.UserDetailsImpl;
import com.bai.service.TokenService;
import com.bai.util.BaseController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
public class AuthenticationController extends BaseController {
	
	private final TokenService tokenService;
	
	@PostMapping
	public ResponseEntity<Token> authentication(@RequestAttribute Authentication authentication) {
		return this.created(tokenService.create(((UserDetailsImpl) authentication.getPrincipal()).getFuncionario()));
	}
	
	@PostMapping("/refresh")
	public ResponseEntity<Token> refreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
		return this.created(tokenService.refresh(refreshTokenDTO.getAuthorization()));
	}	
}
