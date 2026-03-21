package br.com.fiap.tech_challenge_ii.restaurant.core.usecase;

import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantOutput;

public interface CreateRestaurantUseCase {
    CreateRestaurantOutput create(Long id, CreateRestaurantInput restaurant);
}
