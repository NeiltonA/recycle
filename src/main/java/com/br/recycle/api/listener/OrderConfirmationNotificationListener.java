//package com.br.recycle.api.listener;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.event.TransactionalEventListener;
//
//import com.br.recycle.api.event.DonationConfirmedEvent;
//import com.br.recycle.api.model.Donation;
//import com.br.recycle.api.service.EnvioEmailService;
//import com.br.recycle.api.service.EnvioEmailService.Mensagem;
//
//@Component
//public class OrderConfirmationNotificationListener {
//
//	@Autowired
//	private EnvioEmailService emailService;
//	
//	@TransactionalEventListener// fase especifica que o event pode ser dispaado
//	public void aoConfirmarPedido(DonationConfirmedEvent event) {
//		Donation donation =  event.getDonation();
//		var mensagem = Mensagem.builder()
//				.assunto(donation.getUser().getName() + " _ Donation confirmed")
//				.corpo("emails/donation-confirmado.html")
//				.variavel("donation", donation)
//				.destinatario(donation.getUser().getEmail())
//				.build();
//		
//		emailService.enviar(mensagem);
//	}
//
//}
