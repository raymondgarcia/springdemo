package com.mybank.userservice.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @MockBean
    UserService service;

    @Autowired
    MockMvc server;

    @Autowired
    ObjectMapper mapper;


    private User user;

    private UserRequest request;


    @BeforeEach
    void setUp() {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJudWxsIiwiaWF0IjoxNjI5ODM4OTcwLCJzdWIiOiJqdWFuQHJvZHJpZ3VlejEub3JnIiwiaXNzIjoiTWFpbiIsImV4cCI6MTYzMDQ0Mzc3MH0.apMmBl97qrhNPhKa92T4Ph8wV6BgtK7NzQV-wfhdfgs";
        user = new User();
        user.setId(UUID.fromString("ea0ea4a9-0ae3-48e3-ae09-97ef2c649a0c"));
        user.setCreated(LocalDate.of(2021, 8, 24));
        user.setUpdate(LocalDate.of(2021, 8, 24));
        user.setToken(token);
        user.setActive(true);

        Set<Phone> phones = new HashSet<>();
        Phone phone = new Phone();
        phone.setNumber("123456");
        phone.setContrycode("1");
        phone.setCitycode("455");
        phones.add(phone);

        request = UserRequest.builder()
        .name("name")
        .email("email@email.com")
        .password("2Hunter1.)")
        .phones(phones).build();
    }

    @Test
    public void create_user() throws Exception {
        when(service.create(any(User.class))).thenReturn(user);

        server.perform(post("/api/v1/users/")
        .contentType("application/json")
        .content(mapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id", is("ea0ea4a9-0ae3-48e3-ae09-97ef2c649a0c")))
        .andExpect(jsonPath("$.created", is("2021-08-24")))
        .andExpect(jsonPath("$.modified", is("2021-08-24")))
        .andExpect(jsonPath("$.lastLogin", is("2021-08-24")))
        .andExpect(jsonPath("$.token", is(user.getToken())))
        .andExpect(jsonPath("$.isactive", is(true)));
    }


    @Test
    public void should_fail_if_email_is_not_formatted() throws Exception {
        request.setEmail("skdjksjdksjks");

        when(service.create(any(User.class))).thenReturn(user);

        server.perform(post("/api/v1/users/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.mensaje", is("Email no tine el formato adecuado: (aaaaaaa@dominio.cl)")));
    }


    @Test
    public void should_fail_if_password_is_setup_correctly() throws Exception {
        request.setPassword("123345");

        when(service.create(any(User.class))).thenReturn(user);

        server.perform(post("/api/v1/users/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.mensaje", is("password no cumple con los requerimientos: Una Mayúscula, letras minúsculas, y dos números")));

        request.setPassword("12334q");

        server.perform(post("/api/v1/users/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.mensaje", is("password no cumple con los requerimientos: Una Mayúscula, letras minúsculas, y dos números")));

        request.setPassword("1Aq");

        server.perform(post("/api/v1/users/")
                .contentType("application/json")
                .content(mapper.writeValueAsString(request)))
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.mensaje", is("password no cumple con los requerimientos: Una Mayúscula, letras minúsculas, y dos números")));
    }
}
