package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;


import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateRestaurantRequest(@NotBlank(message = "Name is required") String name,
                                      @NotNull(message = "Address is required") @Valid AddressJson address,
                                      @NotBlank(message = "Kitchen type is required") String kitchenType,
                                      @NotBlank(message = "Opening hours is required") String openingHours) {
}
