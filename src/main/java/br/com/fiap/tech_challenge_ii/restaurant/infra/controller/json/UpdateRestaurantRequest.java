package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;

import jakarta.validation.Valid;

public record UpdateRestaurantRequest(String name,
                                      @Valid AddressJson address,
                                      String kitchenType,
                                      String openingHours) {
}
