package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;

public record CreateRestaurantResponse(Long id,
                                       String name,
                                       String kitchenType) {
}
