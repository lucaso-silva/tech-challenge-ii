package br.com.fiap.tech_challenge_ii.core.domain.entities.restaurant;

import java.math.BigDecimal;

import lombok.Getter;

@Getter
public class MenuItem {

    private String name;
    private String description;
    private BigDecimal price;
    private boolean isOnlyLocalConsuption;
    private String photoPath;

    public MenuItem(String name, String description, BigDecimal price, boolean isOnlyLocalConsuption,
            String photoPath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isOnlyLocalConsuption = isOnlyLocalConsuption;
        this.photoPath = photoPath;
    }

}
