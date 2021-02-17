package com.br.recycle.api.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Service
public interface EnvioEmailService {

	void enviar(Mensagem mensagem);
	
	@Getter
	@Builder
	class Mensagem{
		
		@Singular
		private Set<String> destinatarios;
		
		@NonNull
		private String assunto;
		@NonNull
		private String corpo;
		
		@Singular("variavel")
		private Map<String, Object> variaveis;
	}
}
