package com.br.recycle.api.email;

import javax.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("recycle.email")
public class EmailProperties {
	
	private Implementacao impl = Implementacao.FAKE;
	
	@NotNull
	private String remetente;
	
	private Sandbox sandbox = new Sandbox();
	
	public enum Implementacao {
		SMTP, FAKE, SANDBOX
	}
	
	@Getter
	@Setter
	public class Sandbox {
		
		private String destinatario;
		
	}
	
}