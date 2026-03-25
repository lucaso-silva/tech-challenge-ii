package br.com.fiap.tech_challenge_ii.user.core.usecase;

import br.com.fiap.tech_challenge_ii.user.core.dto.UpdateUserDTO;

public interface UpdateUserUseCase {

    void update(Long loggedInUserId, UpdateUserDTO input);

}
