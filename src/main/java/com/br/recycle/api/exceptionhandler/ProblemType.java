package com.br.recycle.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    INVALID_DATA("/dados-invalidos", "Dados inválidos"),
    ACESS_DENIED("/acesso-negado", "Acesso negado"),
    SYSTEM_ERROR("/erro-de-sistema", "Erro de sistema"),
    INVALID_PARAMETER("/parametro-invalido", "Parâmetro inválido"),
    UNREADABLE_MESSAGE("/mensagem-incompreensivel", "Mensagem incompreensível"),
    RESOURCE_NOT_FOUND("/recurso-nao-encontrado", "Recurso não encontrado"),
    ENTITY_IN_USE("/entidade-em-uso", "Entidade em uso"),
    UNPROCESSABLE_ENTITY("/entidade-nao-processada", "Entidade não processada"),
    BUSINESS_ERROR("/erro-na-requisicao", "Falha na chamada"),
	METHOD_NOT_ALLOWED("/erro-na-requisicao", "Falha ao deletar");
	
	
    private String title;
    private String uri;

    ProblemType(String path, String title) {
        this.uri = "https://recycle.com.br" + path;
        this.title = title;
    }

}