package br.com.fiap.tech_challenge_ii.menu.core.usecase;

import java.util.List;

import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;

public interface FindMenuItemUseCase {

    List<MenuItemDTO> findByRestaurantId(Long restaurantId);

}
