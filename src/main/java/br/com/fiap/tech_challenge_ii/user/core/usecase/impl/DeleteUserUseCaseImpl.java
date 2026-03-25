package br.com.fiap.tech_challenge_ii.user.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.DeleteUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteUserUseCaseImpl implements DeleteUserUseCase {

    private final UserGateway userGateway;

    @Override
    public void delete(Long userId) {
        User userById = getUserById(userId);
        userGateway.delete(userById.getId());

    }

    private User getUserById(Long loggedInUseId) {
        return userGateway.findById(loggedInUseId)
                .orElseThrow(UserNotFoundException::new);
    }
}
