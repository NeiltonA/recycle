package com.br.recycle.api.service.email;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;

import com.br.recycle.api.email.EmailProperties;
import com.br.recycle.api.exception.EmailException;
import com.br.recycle.api.model.Message;
import com.sun.el.parser.ParseException;

import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateNotFoundException;

/**
 * Classe responsável por mapear os cenários de testes da classe de serviço do email.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 13/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class SmtpSendEmailServiceTest {

	@Mock
	private Template template;
	
	@Mock
    private JavaMailSender mailSender;

	@Mock
    private EmailProperties emailProperties;

	@Mock
    private Configuration freemarkerConfig;

	@InjectMocks
	private SmtpSendEmailService smtpSendEmailService;
	
	@Test
	public void testSendEmailException() throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException {
		given(freemarkerConfig.getTemplate(getMockMessage().getBody())).willReturn(template);
		assertThrows(EmailException.class, () -> smtpSendEmailService.send(getMockMessage()));
	}

	private Message getMockMessage() {
		return Message.builder()
				.body("Teste")
				.recipient("testando")
				.subject("TesteSubject")
				.build();
	}

}
