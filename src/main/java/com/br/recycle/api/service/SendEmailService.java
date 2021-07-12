package com.br.recycle.api.service;

import org.springframework.stereotype.Service;

import com.br.recycle.api.model.Message;

@Service
public interface SendEmailService {

	void send(Message message);
}
