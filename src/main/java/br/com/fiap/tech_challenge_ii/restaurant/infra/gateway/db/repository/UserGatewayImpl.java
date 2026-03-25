package br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.repository;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.mapper.UserEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
public class UserGatewayImpl implements UserGateway {
    private final UserJPARepository userRepository;
    private final UserEntityMapper mapper;

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id)
                .map(mapper::toDomain);
    }
}
