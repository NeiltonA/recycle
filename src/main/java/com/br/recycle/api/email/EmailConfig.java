//package com.br.recycle.api.email;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.br.recycle.api.service.EnvioEmailService;
//
//
//@Configuration
//public class EmailConfig {
//
//	@Autowired
//	private EmailProperties emailProperties;
//
//	@Bean
//	public EnvioEmailService envioEmailService() {
//		switch (emailProperties.getImpl()) {
//			case SMTP:
//				//return new SmtpEnvioEmailService();
//			default:
//				return null;
//		}
//	}
//
//}