package com.mybank.userservice.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
@Validated
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    @Email(regexp = "^(.+)@(.+)$", message = "Email no tine el formato adecuado: (aaaaaaa@dominio.cl)")
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @CreatedDate
    private LocalDate created;

    @LastModifiedDate
    private LocalDate update;

    private String token;

    private boolean isActive;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Phone> phones  = new HashSet<>();
}
