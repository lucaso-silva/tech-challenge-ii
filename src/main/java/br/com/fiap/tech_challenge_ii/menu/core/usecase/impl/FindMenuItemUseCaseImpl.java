package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.usecase.FindMenuItemUseCase;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FindMenuItemUseCaseImpl implements FindMenuItemUseCase {

    private final RestaurantGateway restaurantGateway;
    private final MenuItemGateway menuItemGateway;

    @Override
    public List<MenuItemDTO> findByRestaurantId(Long restaurantId) {
        restaurantGateway.findRestaurantById(restaurantId)
                .orElseThrow(() -> new RestaurantNotFoundException());

        return menuItemGateway.findByRestaurantId(restaurantId)
                .stream()
                .map(item -> item.toDTO())
                .collect(Collectors.toList());
    }

}
