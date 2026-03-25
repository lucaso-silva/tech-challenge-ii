package br.com.fiap.tech_challenge_ii.user.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.GetUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GetUserUseCaseImpl implements GetUserUseCase {

    private final UserGateway userGateway;

    @Override
    public List<User> findAll() {
        return userGateway.findAll();
    }

    @Override
    public User findById(Long id) {
        return userGateway.findById(id)
                .orElseThrow(() -> {
                    log.warn("Usuário não encontrado com id: {}", id);
                    return new UserNotFoundException();
                });
    }
}