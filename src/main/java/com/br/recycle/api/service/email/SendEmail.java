package com.br.recycle.api.service.email;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Async
public class SendEmail {

	@Value("${recycle.email.remetente}")
	private String mail;

	@Autowired
	private JavaMailSender mailSender;

	public void sendDespatchEmail(String email, String link) throws MessagingException, UnsupportedEncodingException {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

		helper.setFrom(this.mail);
		helper.setTo(email);

		String subject = "Aqui está o link para redefinir sua senha!";

		String content = "<p>Olá,</p>" + "<p>Você solicitou a redefinição de sua senha.</p>"
				+ "<p>Clique no link abaixo para alterá-la. Link válido por 30 minutos!</p>" + "<p><a href=\"" + link
				+ "\">Mudar minha senha</a></p>" + "<br>" + "<p>Ignore este e-mail se você se lembra da sua senha, "
				+ "ou você não fez o pedido.</p>";

		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);
	}
}
