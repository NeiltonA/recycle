package com.br.recycle.api.config;



import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
public class DataSourceConfigTest {
	
	@InjectMocks
	private DataSourceConfig config = new DataSourceConfig();
	
	@BeforeEach
	public void setUp() {
		ReflectionTestUtils.setField(config, "url", "jdbc:h2:mem:testdb");
		ReflectionTestUtils.setField(config, "username", "sa");
		ReflectionTestUtils.setField(config, "password", "sa");
		ReflectionTestUtils.setField(config, "driverClassName", "org.h2.Driver");
	}
	
	@Test
	public void testDataSource() {
		DataSource dataSource = config.dataSource();
		assertNotNull(dataSource);
	}
}
