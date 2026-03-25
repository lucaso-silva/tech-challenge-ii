package br.com.fiap.tech_challenge_ii.user.core.usecase.impl;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.UpdateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.UpdateUserUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

    private final UserGateway userGateway;

    @Override
    public void update(Long loggedInUserId, UpdateUserDTO input) {
        User userById = getUserById(loggedInUserId);

        userById.update(input.name(), input.userType());

        userGateway.update(userById);

    }

    private User getUserById(Long loggedInUseId) {
        return userGateway.findById(loggedInUseId)
                .orElseThrow(UserNotFoundException::new);
    }

}
