package br.com.fiap.tech_challenge_ii.menu.infra.controller.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record MenuItemRequestDTO(
        @NotBlank(message = "Name is required") String name,

        @NotBlank(message = "Description is required") String description,

        @NotNull(message = "Price is required") @Positive(message = "Price must be positive") BigDecimal price,

        boolean isOnlyLocalConsuption,

        String photoPath) {
}
