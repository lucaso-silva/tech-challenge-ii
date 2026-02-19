package br.com.fiap.tech_challenge_ii.user.core.domain;

import java.time.LocalDateTime;

import br.com.fiap.tech_challenge_ii.core.domain.valueObjects.Address;
import lombok.Getter;

@Getter
public abstract class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;
    private LocalDateTime lastModifiedDate;
    private Address address;

    public User(Long id, String name, String email, String login, String password, LocalDateTime lastModifiedDate,
            Address address) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.password = password;
        this.lastModifiedDate = lastModifiedDate;
        this.address = address;
    }

}
