package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;

import java.util.List;

public interface GetUserUseCase {
    List<User> findAll();
    User findById(Long id);
}
