package br.com.fiap.tech_challenge_ii.user.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public abstract class User {
    protected Long id;
    protected String name;
    protected String email;
    protected String userType;

    protected User(Long id, String name, String email, String nameType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userType = nameType;
    }

    public void update(String name, String userType) {
        this.name = name;
        this.userType = userType;
    }

}
