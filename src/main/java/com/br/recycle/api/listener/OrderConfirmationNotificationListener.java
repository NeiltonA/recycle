package com.br.recycle.api.listener;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.recycle.api.event.DonationConfirmedEvent;
import com.br.recycle.api.exception.EmailException;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.service.ContactService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sendgrid.Email;
import com.sendgrid.Mail;
import com.sendgrid.Method;
import com.sendgrid.Personalization;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

@Component
public class OrderConfirmationNotificationListener {
private static final Logger logger = LoggerFactory.getLogger(ContactService.class);
	
	@Value("${recycle.email.remetente}")
	private String mail;
	
	@Value("${recycle.sendgrid.api-key}")
	private String key;
	
	@Value("${recycle.sendgrid.template.confirm.Id}")
	private String templateIde;
	

    @TransactionalEventListener// fase especifica que o evento pode ser disparado
    public void onCancelOrder(DonationConfirmedEvent event) throws IOException {
        Donation donation = event.getDonation();
        sendMail(donation);
        

    	}
        

    	public String sendMail(Donation donation) throws IOException {

    		Email from = new Email(this.mail);
    		Email to = new Email(donation.getGiver().getUser().getEmail());
    		Mail mail = new Mail();
    		DynamicTemplatePersonalization personalization = new DynamicTemplatePersonalization();
    		personalization.addTo(to);
    		mail.setFrom(from);
    		mail.setSubject("The subject");

    		personalization.addDynamicTemplateData(donation);
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

    		public void addDynamicTemplateData(Donation donation) {
    			if (dynamic_template_data == null) {
    				dynamic_template_data = new HashMap<String, String>();
    				dynamic_template_data.put("name", donation.getGiver().getUser().getName());
    				dynamic_template_data.put("code", donation.getCode());
    				
    				String date = donation.getDateConfirmation().toString().toString();
    				OffsetDateTime dt = OffsetDateTime.parse(date);
    				DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    				String dateform = fmt.format(dt);
    				dynamic_template_data.put("dateConfirmation", dateform);
    				if(donation.getStatus().name() == "CONFIRMED") {
    					dynamic_template_data.put("status", "Confirmado");
    				}
    			} else {
    				dynamic_template_data.put("", "");
    			}
    		}

    	}
}
