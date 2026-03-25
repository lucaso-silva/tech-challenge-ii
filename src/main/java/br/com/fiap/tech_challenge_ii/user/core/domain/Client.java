package br.com.fiap.tech_challenge_ii.user.core.domain;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Client extends User {

    public Client(Long id, String name, String userType, String email) {
        super(id, name, email, userType);
    }

}
