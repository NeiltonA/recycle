package com.br.recycle.api.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.recycle.api.event.DonationCancelEvent;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.model.Message;
import com.br.recycle.api.service.SendEmailService;

@Component
public class OrderCanceledNotificationListener {

    @Autowired
    private SendEmailService emailService;

    @TransactionalEventListener// fase especifica que o evento pode ser disparado
    public void onCancelOrder(DonationCancelEvent event) {
        Donation donation = event.getDonation();
        var message = Message.builder()
                .subject(donation.getGiver().getUser().getName() + " _ Doação Cancelada")
                .body("emails/donation-cancel.html")
                .variavel("donation", donation)
                .recipient(donation.getGiver().getUser().getEmail())
                .build();

        emailService.send(message);
    }

}
