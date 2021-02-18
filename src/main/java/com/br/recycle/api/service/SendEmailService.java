package com.br.recycle.api.service;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Service
public interface SendEmailService {

    void send(Message message);

    @Getter
    @Builder
    class Message {

        @Singular
        private Set<String> recipients;

        @NonNull
        private String subject;
        @NonNull
        private String body;

        @Singular("variavel")
        private Map<String, Object> variables;
    }
}
