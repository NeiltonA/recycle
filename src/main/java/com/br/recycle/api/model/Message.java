package com.br.recycle.api.model;

import java.util.Map;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Singular;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
