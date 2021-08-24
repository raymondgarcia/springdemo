package com.mybank.userservice.user;

import com.mybank.userservice.base.RecordNotFoundException;
import com.mybank.userservice.util.JWTUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private JWTUtil jwtUtil;

    public User create(User user) {
        Set<Phone> phones = user.getPhones().stream().map(phone -> addUser(user, phone)).collect(Collectors.toSet());
        String tokenJwt = jwtUtil.create(String.valueOf(user.getId()), user.getEmail());
        user.setActive(true);
        user.setToken(tokenJwt);
        user.setPhones(phones);
        return repository.save(user);
    }

    public String getPasswordHash(String password) {
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1, 1024, 1, password.toCharArray());
        return hash;
    }

    private Phone addUser(User user, Phone phone) {
        phone.setUser(user);
        return phone;
    }

    public User update(UUID id, User user) {
        user.setId(id);
        return repository.save(user);
    }

    public User get(UUID id) {
        return repository.findById(id).orElseThrow(() -> new RecordNotFoundException(String.format("Could")));
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    public List<User> list() {
        return repository.findAll();
    }

}
