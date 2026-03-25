package br.com.fiap.tech_challenge_ii.user.core.gateway;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import br.com.fiap.tech_challenge_ii.user.core.dto.CreateUserDTO;

import java.util.List;
import java.util.Optional;

public interface UserGateway {

    Optional<User> findById(Long loggedInUserId);

    void update(User user);

    void delete(Long id);

    User save(User user);

    Boolean existsByEmail(String email);

    User createFromDTO(CreateUserDTO createUserDTO);

    List<User> findAll();
}
