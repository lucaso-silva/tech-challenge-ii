package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper.UserMapper;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSpringDataGateway implements UserGateway {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId)
                .map(userMapper::toDomain);
    }

}
