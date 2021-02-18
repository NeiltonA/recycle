package com.br.recycle.api.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import com.br.recycle.api.event.DonationConfirmedEvent;
import com.br.recycle.api.model.Donation;
import com.br.recycle.api.service.SendEmailService;
import com.br.recycle.api.service.SendEmailService.Message;

@Component
public class OrderConfirmationNotificationListener {

    @Autowired
    private SendEmailService emailService;

    @TransactionalEventListener // fase específica que o evento pode ser disparado
    public void onOrderConfirm(DonationConfirmedEvent event) {
        Donation donation = event.getDonation();
        var message = Message.builder()
                .subject(donation.getGiver().getUser().getName() + " _ Donation confirmed")
                .body("emails/donation-confirmation.html")
                .variavel("donation", donation)
                .recipient(donation.getGiver().getUser().getEmail())
                .build();

        emailService.send(message);
    }

}
