package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuUserEntity;

@Component
public class UserMapper {

    public User toDomain(MenuUserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        return new User(
                userEntity.getId(),
                userEntity.getName());
    }

}
