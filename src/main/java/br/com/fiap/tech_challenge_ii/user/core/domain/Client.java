package br.com.fiap.tech_challenge_ii.user.core.domain;

import java.time.LocalDateTime;

import br.com.fiap.tech_challenge_ii.user.core.domain.valueObjects.Address;
import lombok.Getter;

@Getter
public class Client extends User {

    public Client(Long id, String name, String email, String login, String password, LocalDateTime lastModifiedDate,
            Address address) {
        super(id, name, email, login, password, lastModifiedDate, address);
    }

}
