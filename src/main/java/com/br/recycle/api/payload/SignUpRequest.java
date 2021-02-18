package com.br.recycle.api.payload;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import com.br.recycle.api.model.Address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequest {

    private String name;

    private String username;

    private String email;

    private String password;

    private String cellPhone;

    private String cpfCnpj;

    @Enumerated
    @NotNull
    private RoleName role;

    private List<Address> address = new ArrayList<>();
}
