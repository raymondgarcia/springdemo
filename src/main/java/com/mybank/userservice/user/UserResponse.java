package com.mybank.userservice.user;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class UserResponse {
    private UUID id;
    LocalDate created;
    LocalDate modified;
    LocalDate lastLogin;
    String token;
    boolean isactive;
}
