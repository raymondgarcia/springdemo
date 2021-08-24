package com.mybank.userservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/users/")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(@Valid @RequestBody UserRequest userRequest) {
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

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public User update(@Valid @RequestBody User user, @PathVariable UUID id) {
        return service.update(id, user);
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public User get(@PathVariable UUID id) {
        User response = service.get(id);
        response.getPhones();
        return response;
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }

    @GetMapping("/")
    public List<User> list() {
        return service.list();
    }

}
