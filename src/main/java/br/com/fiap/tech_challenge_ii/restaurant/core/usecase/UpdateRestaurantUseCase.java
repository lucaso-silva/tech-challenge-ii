package br.com.fiap.tech_challenge_ii.restaurant.core.usecase;

import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantOutput;

public interface UpdateRestaurantUseCase {
    UpdateRestaurantOutput update(Long userId, Long restaurantId, UpdateRestaurantInput input);
}
