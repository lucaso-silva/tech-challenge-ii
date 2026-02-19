package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;

public record CreateRestaurantOutput(Long id,
                                     String name,
                                     String kitchenType) {
    public static CreateRestaurantOutput from(Restaurant restaurant) {
        return new CreateRestaurantOutput(restaurant.getId(),
                restaurant.getName(),
                restaurant.getKitchenType().toString());
    }
}
