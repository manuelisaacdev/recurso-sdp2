package com.bfa.security;

import org.springframework.context.MessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.bfa.repository.FuncionarioRepository;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final HttpServletRequest request;
	private final MessageSource messageSource;	
	private final FuncionarioRepository funcionarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return new UserDetailsImpl(funcionarioRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException(messageSource.getMessage("Funcionario.email.senha.invalida", null, request.getLocale()))));
	}
}
