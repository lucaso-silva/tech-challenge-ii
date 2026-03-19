package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuItemEntity;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.mapper.MenuItemMapper;
import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.repository.MenuItemRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MenuItemSpringDataGateway implements MenuItemGateway {

    private final MenuItemRepository menuItemRepository;
    private final MenuItemMapper menuItemMapper;

    @Override
    public Optional<MenuItem> findByMenuItemNameAndRestaurantId(String name, Long restaurantId) {
        return menuItemRepository.findByNameAndRestaurantId(name, restaurantId)
                .map(menuItemMapper::toDomain);

    }

    @Override
    public List<MenuItem> findByRestaurantId(Long restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId).stream()
                .map(menuItemMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public MenuItem save(MenuItem menuItem) {
        MenuItemEntity entity = menuItemMapper.toEntity(menuItem);
        MenuItemEntity savedEntity = menuItemRepository.save(entity);
        return menuItemMapper.toDomain(savedEntity);
    }

    @Override
    public void delete(MenuItem menuItem) {
        menuItemRepository.delete(menuItemMapper.toEntity(menuItem));
    }

    @Override
    public Optional<MenuItem> findById(Long menuId) {
        return menuItemRepository.findById(menuId)
                .map(menuItemMapper::toDomain);
    }

    @Override
    public Optional<MenuItem> findByMenuItemNameAndRestaurantIdExcludingId(String name, Long restaurantId,
            Long excludeId) {
        return menuItemRepository.findByNameAndRestaurantIdAndIdNot(name, restaurantId, excludeId)
                .map(menuItemMapper::toDomain);
    }

}
