package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.KitchenType;

public record GetRestaurantResponse(Long id,
                                    String name,
                                    AddressJson address,
                                    KitchenType kitchenType,
                                    String openingHours) {
}
