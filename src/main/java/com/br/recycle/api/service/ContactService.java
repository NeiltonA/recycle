package com.br.recycle.api.service;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.br.recycle.api.exception.EmailException;
import com.br.recycle.api.payload.ContactInput;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;


@Service
public class ContactService {
	
	private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	@Value("${recycle.email.remetente}")
	private String mail;
	
	@Value("${recycle.sendgrid.api-key}")
	private String key;
	
	@Value("${recycle.sendgrid.template.Id}")
	private String templateIde;

	public String send(ContactInput contact) throws IOException {

		Email from = new Email(this.mail);
		Email to = new Email(this.mail);
		Mail mail = new Mail();
		DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
		personalization.addTo(to);
		mail.setFrom(from);
		mail.setSubject("The subject");

		personalization.addDynamicTemplateData(contact);
		mail.addPersonalization(personalization);
		mail.setTemplateId(this.templateIde);

		SendGrid sg = new SendGrid(this.key);
		Request request = new Request();

		try {
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody(mail.build());
			Response response = sg.api(request);
			logger.info(response.getBody());
			return response.getBody();
		} catch (IOException ex) {
			 throw new EmailException("Não foi possível enviar o e-mail", ex);
		}
	}
 
	private static class DynamicTemplatePersonalization extends Personalization {

		@JsonProperty(value = "dynamic_template_data")
		private Map<String, String> dynamic_template_data;

		@JsonProperty("dynamic_template_data")
		public Map<String, String> getDynamicTemplateData() {
			if (dynamic_template_data == null) {
				return Collections.<String, String>emptyMap();
			}
			return dynamic_template_data;
		}

		public void addDynamicTemplateData(ContactInput contact) {
			if (dynamic_template_data == null) {
				dynamic_template_data = new HashMap<String, String>();
				dynamic_template_data.put("name", contact.getName());
				dynamic_template_data.put("email", contact.getEmail());
				dynamic_template_data.put("message", contact.getMessage());
			} else {
				dynamic_template_data.put("", "");
			}
		}

	}

}
