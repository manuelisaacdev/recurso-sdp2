package com.intermediario.service.impl;

import java.io.IOException;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.intermediario.exception.InternalServerErrorRequestException;
import com.intermediario.exception.UnauthorizedException;
import com.intermediario.service.StatusContaService;

@Service
public class StatusContaServiceImpl implements StatusContaService {
	private final String baiURL;
	private final String baiAccessToken;
	private final String baiPrefixIban;
	
	private final String bfaURL;
	private final String bfaAccessToken;
	private final String bfaPrefixIban;
	
	public StatusContaServiceImpl(
			@Value("${application.bai.url}") String baiURL, 
			@Value("${application.bai.prefix-iban}") String baiPrefixIban, 
			@Value("${application.bai.access-token}") String baiAccessToken, 
			@Value("${application.bfa.url}") String bfaURL,
			@Value("${application.bfa.prefix-iban}") String bfaPrefixIban,
			@Value("${application.bfa.access-token}") String bfaAccessToken) {
		super();
		this.baiURL = baiURL;
		this.baiPrefixIban = baiPrefixIban;
		this.baiAccessToken = baiAccessToken;
		
		this.bfaURL = bfaURL;
		this.bfaPrefixIban = bfaPrefixIban;
		this.bfaAccessToken = bfaAccessToken;
	}

	private String get(String url, String authorization) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            request.addHeader(HttpHeaders.AUTHORIZATION, authorization);
            CloseableHttpResponse response = httpClient.execute(request);
            String result =  EntityUtils.toString(response.getEntity());
            response.close();
            return result;
        } catch (IOException e) {
			e.printStackTrace();
			throw new InternalServerErrorRequestException(e);
		}
	}

	@Override
	public String findConta(String iban) {
		if (iban.startsWith(baiPrefixIban)) 
			return this.get(String.format("%s/contas/iban/%s", baiURL, iban), baiAccessToken);
		else if (iban.startsWith(bfaPrefixIban)) 
			return this.get(String.format("%s/contas/iban/%s", bfaURL, iban), bfaAccessToken);
		throw new UnauthorizedException("Iban não registrado");
	}

	@Override
	public String findAllDeposito(String iban) {
		if (iban.startsWith(baiPrefixIban)) 
			return this.get(String.format("%s/depositos?iban=%s", baiURL, iban), baiAccessToken);
		else if (iban.startsWith(bfaPrefixIban)) 
			return this.get(String.format("%s/depositos?iban=%s", bfaURL, iban), bfaAccessToken);
		throw new UnauthorizedException("Iban não registrado");
	}

	@Override
	public String findAllLevantamento(String iban) {
		if (iban.startsWith(baiPrefixIban)) 
			return this.get(String.format("%s/levantamentos?iban=%s", baiURL, iban), baiAccessToken);
		else if (iban.startsWith(bfaPrefixIban)) 
			return this.get(String.format("%s/levantamentos?iban=%s", bfaURL, iban), bfaAccessToken);
		throw new UnauthorizedException("Iban não registrado");
	}

	@Override
	public String findAllTransferencia(String iban) {
		if (iban.startsWith(baiPrefixIban)) 
			return this.get(String.format("%s/transferencias/iban/%s", baiURL, iban), baiAccessToken);
		else if (iban.startsWith(bfaPrefixIban)) 
			return this.get(String.format("%s/transferencias/iban/%s", bfaURL, iban), bfaAccessToken);
		throw new UnauthorizedException("Iban não registrado");
	}

}
