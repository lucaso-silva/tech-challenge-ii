package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.RestaurantEntity;

@Component
public class RestaurantMapper {

    public Restaurant toDomain(RestaurantEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Restaurant(
                entity.getId(),
                entity.getName(),
                entity.getOwnerId());
    }

    public RestaurantEntity toEntity(Restaurant domain) {
        if (domain == null) {
            return null;
        }
        RestaurantEntity entity = new RestaurantEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setOwnerId(domain.getOwnerId());
        return entity;
    }

}
