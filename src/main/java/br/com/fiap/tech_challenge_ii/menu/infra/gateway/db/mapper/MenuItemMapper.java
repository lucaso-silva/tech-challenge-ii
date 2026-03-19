package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper;

import org.springframework.stereotype.Component;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuItemEntity;

@Component
public class MenuItemMapper {

    public MenuItem toDomain(MenuItemEntity entity) {
        if (entity == null) {
            return null;
        }
        return new MenuItem(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getIsOnlyLocalConsumption(),
                entity.getPhotoPath(),
                entity.getRestaurantId());
    }

    public MenuItemEntity toEntity(MenuItem domain) {
        if (domain == null) {
            return null;
        }
        MenuItemEntity entity = new MenuItemEntity();
        if (domain.getId() != null) {
            entity.setId(domain.getId());
        }
        entity.setName(domain.getName());
        entity.setDescription(domain.getDescription());
        entity.setPrice(domain.getPrice());
        entity.setIsOnlyLocalConsumption(domain.isOnlyLocalConsuption());
        entity.setPhotoPath(domain.getPhotoPath());
        entity.setRestaurantId(domain.getRestaurantId());
        return entity;
    }

}
