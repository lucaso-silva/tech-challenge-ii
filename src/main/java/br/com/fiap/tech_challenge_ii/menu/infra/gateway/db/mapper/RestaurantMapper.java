package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuRestaurantEntity;

@Component
public class RestaurantMapper {

    public Restaurant toDomain(MenuRestaurantEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getOwnerId());
    }

    public MenuRestaurantEntity toEntity(Restaurant domain) {
        if (domain == null) {
            return null;
        }
        MenuRestaurantEntity entity = new MenuRestaurantEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setOwnerId(domain.getOwnerId());
        return entity;
    }

}
