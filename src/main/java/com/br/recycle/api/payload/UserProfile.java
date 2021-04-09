package com.br.recycle.api.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserProfile {
    private Long id;
    private String username;
    private String name;

}
