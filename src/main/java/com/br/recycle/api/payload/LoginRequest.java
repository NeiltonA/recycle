package com.br.recycle.api.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	
    private String email;

    private String password;

}
