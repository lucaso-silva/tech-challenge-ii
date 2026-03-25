package br.com.fiap.tech_challenge_ii.menu.core.gateway;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;

public interface MenuItemGateway {

    MenuItem save(MenuItem menuItem);

    Optional<MenuItem> findByMenuItemNameAndRestaurantId(String name, Long restaurantId);

    List<MenuItem> findByRestaurantId(Long restaurantId);

    void delete(MenuItem menuItemId);

    Optional<MenuItem> findById(Long menuId);

    Optional<MenuItem> findByMenuItemNameAndRestaurantIdExcludingId(String name, Long restaurantId, Long excludeId);

    List<MenuItem> findAll();

}
