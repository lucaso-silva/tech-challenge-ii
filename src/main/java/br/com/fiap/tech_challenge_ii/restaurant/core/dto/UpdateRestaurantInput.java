package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

public record UpdateRestaurantInput(String name,
                                    AddressDTO address,
                                    String kitchenType,
                                    String openingHours) {
}
