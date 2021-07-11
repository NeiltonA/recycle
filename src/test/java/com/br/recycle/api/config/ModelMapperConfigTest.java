package com.br.recycle.api.config;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

/**
 * Classe de teste responsável por mapear os cenários de configuração do Mapper.
 * 
 * @author Caio Henrique do Carmo Bastos
 * @since 10/07/2021
 *
 */
@ExtendWith(MockitoExtension.class)
public class ModelMapperConfigTest {

	@InjectMocks
	private ModelMapperConfig modelMapperConfig;

	/**
	 * Método responsável por mapear o cenário de configuração.
	 */
	@Test
	public void testModelMapperSuccess() {
		ModelMapper modelMapper = modelMapperConfig.modelMapper();
		assertNotNull(modelMapper);
	}
}