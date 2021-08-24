package com.mybank.userservice.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Set;

@Getter
@Setter
@Builder
public class UserRequest {

    @NotNull
    private String name;

    @NotNull
    @Email(regexp = "^(.+)@(.+)$", message = "Email no tine el formato adecuado: (aaaaaaa@dominio.cl)")
    private String email;

    @NotNull
    @Pattern(regexp = "^((?=.*[0-9]){2})(?=.*[a-z])(?=.*[A-Z]).{4,20}$",
            message = "password no cumple con los requerimientos: Una Mayúscula, letras minúsculas, y dos números")
    private String password;

    private Set<Phone> phones;
}
