package br.com.fiap.tech_challenge_ii.user.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;
import br.com.fiap.tech_challenge_ii.user.core.exception.UserAlreadyExistsException;
import br.com.fiap.tech_challenge_ii.user.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.user.core.usecase.CreateUserUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CreateUserUseCaseImpl implements CreateUserUseCase {

    private final UserGateway userGateway;

    @Override
    public User create(CreateUserDTO createUserDTO) {
        if (userGateway.existsByEmail(createUserDTO.email())) {
            log.warn("Usuário já existe com email: {}", createUserDTO.email());
            throw new UserAlreadyExistsException(createUserDTO.email());
        }
        User newUser = userGateway.createFromDTO(createUserDTO);
        return userGateway.save(newUser);
    }
}