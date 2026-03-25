package br.com.fiap.tech_challenge_ii.restaurant.core.usecase;

import br.com.fiap.tech_challenge_ii.restaurant.core.dto.GetRestaurantOutput;

public interface GetRestaurantByIdUseCase {
    GetRestaurantOutput getById(Long id);
}
