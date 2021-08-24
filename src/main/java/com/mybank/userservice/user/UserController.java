package com.mybank.userservice.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest userRequest) {
        logger.info("creating user");
        User user = transform(userRequest);
        User response = service.create(user);
        return UserResponse.builder()
                .id(response.getId())
                .created(response.getCreated())
                .modified(response.getUpdate())
                .isactive(response.isActive())
                .token(response.getToken())
                .lastLogin(response.getCreated())
                .build();
    }

    private User transform(UserRequest userRequest) {
        User user =  new User();
        user.setEmail(userRequest.getEmail());
        user.setName(userRequest.getName());
        user.setPassword(service.getPasswordHash(userRequest.getPassword()));
        user.setPhones(userRequest.getPhones());
        return user;
    }
}
