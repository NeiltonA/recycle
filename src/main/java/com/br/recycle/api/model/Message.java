package com.br.recycle.api.model;

import java.util.Map;
import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

@Getter
@Builder
public class Message {

    @Singular
    private Set<String> recipients;

    @NonNull
    private String subject;
    @NonNull
    private String body;

    @Singular("variavel")
    private Map<String, Object> variables;
}
