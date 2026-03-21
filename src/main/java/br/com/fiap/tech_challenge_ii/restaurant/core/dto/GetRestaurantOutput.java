package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.KitchenType;

public record GetRestaurantOutput(Long id,
                                  String name,
                                  AddressDTO addressDTO,
                                  KitchenType kitchenType,
                                  String openingHours) {
}
