package br.com.fiap.tech_challenge_ii.user.core.gateway;

import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserTypeGateway {

    Long save(Long idRestaurante, String nameType, Class<? extends User> newTypeUser);
}
