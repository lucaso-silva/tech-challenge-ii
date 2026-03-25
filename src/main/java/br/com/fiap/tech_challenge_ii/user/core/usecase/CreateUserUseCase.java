package br.com.fiap.tech_challenge_ii.user.core.usecase;


import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;

public interface CreateUserUseCase {

    User create(CreateUserDTO createUserDTO);
	
}
