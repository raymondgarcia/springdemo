package com.mybank.userservice.user;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="phones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public Phone() {
    }

    private String number;

    private String citycode;

    private String contrycode;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
