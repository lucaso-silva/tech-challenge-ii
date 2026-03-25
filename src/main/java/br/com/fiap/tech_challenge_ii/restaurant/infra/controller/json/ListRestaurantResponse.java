package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;

public record ListRestaurantResponse(Long id,
                                     String name,
                                     String kitchenType) {
}
