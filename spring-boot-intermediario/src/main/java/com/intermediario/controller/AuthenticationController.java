package com.intermediario.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intermediario.dto.RefreshTokenDTO;
import com.intermediario.model.Token;
import com.intermediario.security.UserDetailsImpl;
import com.intermediario.service.TokenService;
import com.intermediario.util.BaseController;

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
