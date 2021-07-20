package com.br.recycle.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.recycle.api.model.Message;
import com.br.recycle.api.payload.ContactInput;


@Service
public class ContactService {

	@Autowired
    private SendEmailService emailService;

    @TransactionalEventListener// fase especifica que o evento pode ser disparado
	public void send(ContactInput contact) {
       
        var message = Message.builder()
                .subject(contact.getName() + " Contato")
                .body("emails/contact.html")
                .variavel("contact", contact)
                .recipient(contact.getEmail())
                .build();

        emailService.send(message);
    }

}