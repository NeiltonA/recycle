package com.br.recycle.api.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class DataSourceConfig {

	@Value("${datasource.url}")
	protected String url;
	
	@Value("${datasource.username}")
	protected String username;
	
	@Value("${datasource.password}")
	protected String password;
	
	@Value("${datasource.class-name}")
	protected String driverClassName;

	@Primary
	@Bean(name = "dataSource")
	public DataSource dataSource() {

		HikariConfig config = new HikariConfig();
		config.setDriverClassName(this.driverClassName);
		config.setJdbcUrl(this.url);
		config.setUsername(this.username);
		config.setPassword(this.password);
		config.setIdleTimeout(120);
		HikariDataSource dbase = new HikariDataSource(config);
		log.info("Conectado na base -> {} ", this.url);
		return dbase;
	}
}