package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;
import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.exception.ExistingMenuItemException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UnauthorizedException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.CreateMenuItemUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateMenuItemUseCaseImpl implements CreateMenuItemUseCase {

    private final MenuItemGateway menuItemGateway;
    private final RestaurantGateway restaurantGateway;
    private final UserGateway userGateway;

    @Override
    public List<Long> save(List<MenuItemDTO> newMenuItens, Long userId) {

        return newMenuItens.stream()
                .map(newItem -> {
                    verifyIfUserExists(userId);

                    Restaurant restaurant = getRestaurant(newItem);

                    if (!restaurant.isOwnedBy(userId)) {
                        throw new UnauthorizedException();
                    }

                    verifyIfMenuItemAlreadyExists(newItem);

                    MenuItem itemSaved = menuItemGateway.save(new MenuItem(
                            newItem.name(),
                            newItem.description(),
                            newItem.price(),
                            newItem.isOnlyLocalConsuption(),
                            newItem.photoPath(),
                            newItem.restaurantId()));
                    return itemSaved.getId();
                })
                .toList();
    }

    private void verifyIfMenuItemAlreadyExists(MenuItemDTO newItem) {
        menuItemGateway
                .findByMenuItemNameAndRestaurantId(newItem.name(), newItem.restaurantId())
                .ifPresent(existing -> {
                    throw new ExistingMenuItemException();
                });
    }

    private void verifyIfUserExists(Long userId) {
        userGateway.findUserById(userId)
                .orElseThrow(() -> new UserNotFoundException());
    }

    private Restaurant getRestaurant(MenuItemDTO newItem) {
        Restaurant restaurant = restaurantGateway
                .findRestaurantById(newItem.restaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException());
        return restaurant;
    }

}
