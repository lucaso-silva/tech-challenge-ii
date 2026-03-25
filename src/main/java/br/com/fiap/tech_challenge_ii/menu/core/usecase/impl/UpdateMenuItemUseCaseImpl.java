package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;
import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.exception.ExistingMenuItemException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.MenuItemNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UnauthorizedException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.UpdateMenuItemUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UpdateMenuItemUseCaseImpl implements UpdateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final UserGateway userGateway;
    private final RestaurantGateway restaurantGateway;

    @Override
    public MenuItemDTO update(Long menuItemId, MenuItemDTO dto, Long userId) {
        verifyIfUserExists(userId);

        MenuItem menuItem = getMenuItemById(menuItemId);

        Restaurant restaurant = getRestaurantById(menuItem.getRestaurantId());

        if (!restaurant.isOwnedBy(userId)) {
            throw new UnauthorizedException();
        }

        verifyDuplicateName(menuItemId, dto.name(), menuItem.getRestaurantId());

        menuItem.update(
                dto.name(),
                dto.description(),
                dto.price(),
                dto.isOnlyLocalConsuption(),
                dto.photoPath());

        MenuItem updatedItem = menuItemGateway.save(menuItem);

        return updatedItem.toDTO();
    }

    private void verifyDuplicateName(Long menuItemId, String newName, Long restaurantId) {
        menuItemGateway.findByMenuItemNameAndRestaurantIdExcludingId(newName, restaurantId, menuItemId)
                .ifPresent(existing -> {
                    throw new ExistingMenuItemException();
                });
    }

    private MenuItem getMenuItemById(Long menuItemId) {
        return menuItemGateway.findById(menuItemId)
                .orElseThrow(() -> new MenuItemNotFoundException());
    }

    private void verifyIfUserExists(Long userId) {
        userGateway.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    private Restaurant getRestaurantById(Long restaurantId) {
        return restaurantGateway.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RuntimeException());
    }

}
