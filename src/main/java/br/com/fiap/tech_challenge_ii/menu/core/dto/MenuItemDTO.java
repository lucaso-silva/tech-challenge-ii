package br.com.fiap.tech_challenge_ii.menu.core.dto;

import java.math.BigDecimal;

public record MenuItemDTO(
        String name,
        String description,
        BigDecimal price,
        boolean isOnlyLocalConsuption,
        String photoPath,
        Long restaurantId) {
}
