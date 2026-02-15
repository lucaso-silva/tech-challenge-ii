package br.com.fiap.tech_challenge_ii.core.domain.entities.user;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.tech_challenge_ii.core.domain.entities.order.Order;
import br.com.fiap.tech_challenge_ii.core.domain.valueObjects.Address;
import lombok.Getter;

@Getter
public class Client extends User {

    private List<Order> orders;

    public Client(Long id, String name, String email, String login, String password, LocalDateTime lastModifiedDate,
            Address address) {
        super(id, name, email, login, password, lastModifiedDate, address);
    }

}
