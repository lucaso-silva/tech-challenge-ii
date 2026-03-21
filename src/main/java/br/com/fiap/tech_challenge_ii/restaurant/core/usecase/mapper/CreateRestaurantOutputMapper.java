package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantOutput;

public class CreateRestaurantOutputMapper {
    public static CreateRestaurantOutput from(Restaurant restaurant) {
        return new CreateRestaurantOutput(restaurant.getId(),
                restaurant.getName(),
                restaurant.getKitchenType().toString());
    }
}
