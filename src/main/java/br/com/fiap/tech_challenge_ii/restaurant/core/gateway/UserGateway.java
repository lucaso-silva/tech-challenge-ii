package br.com.fiap.tech_challenge_ii.restaurant.core.gateway;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.User;

import java.util.Optional;

public interface UserGateway {
    Optional<User> getUserById(Long id);
}
