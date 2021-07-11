package com.br.recycle.api.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Classe de teste responsável por mapear os cenários de configuração do Validation.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class ValidationConfigTest {

	@InjectMocks
	private ValidationConfig validationConfig;
	
	@Mock
	MessageSource MessageSource;
	
	/**
	 * Método resonsável por validar o cenário de configuração da validação.
	 */
	@Test
	public void testValidatorSuccess() {
		LocalValidatorFactoryBean validator = validationConfig.validator(MessageSource);
		assertNotNull(validator);
	}
}
