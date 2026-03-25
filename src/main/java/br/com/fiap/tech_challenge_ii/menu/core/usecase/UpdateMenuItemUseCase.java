package br.com.fiap.tech_challenge_ii.menu.core.usecase;

import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;

public interface UpdateMenuItemUseCase {

    MenuItemDTO update(Long menuItemId, MenuItemDTO dto, Long userId);

}
