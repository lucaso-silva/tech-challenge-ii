package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json;

import jakarta.validation.constraints.NotBlank;

public record AddressJson(Long id,
                          @NotBlank(message = "Street is required") String street,
                          @NotBlank(message = "Number is required") String number,
                          @NotBlank(message = "Neighborhood is required") String neighborhood,
                          @NotBlank(message = "City is required") String city,
                          @NotBlank(message = "Zip Code is required") String zipCode) {
}
