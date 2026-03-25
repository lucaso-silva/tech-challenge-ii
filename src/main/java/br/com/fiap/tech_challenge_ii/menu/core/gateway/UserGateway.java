package br.com.fiap.tech_challenge_ii.menu.core.gateway;

import java.util.Optional;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.User;

public interface UserGateway {

    Optional<User> findUserById(Long userId);

}
