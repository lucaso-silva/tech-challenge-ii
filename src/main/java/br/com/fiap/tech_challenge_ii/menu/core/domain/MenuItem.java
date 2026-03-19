package br.com.fiap.tech_challenge_ii.menu.core.domain;

import java.math.BigDecimal;

import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import lombok.Getter;

@Getter
public class MenuItem {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isOnlyLocalConsuption;
    private String photoPath;
    private Long restaurantId;

    public MenuItem(Long id, String name, String description, BigDecimal price, boolean isOnlyLocalConsuption,
            String photoPath, Long restaurantId) {
        this.setId(id);
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setOnlyLocalConsuption(isOnlyLocalConsuption);
        this.setPhotoPath(photoPath);
        this.setRestaurantId(restaurantId);
    }

    public MenuItem(String name, String description, BigDecimal price, boolean isOnlyLocalConsuption,
            String photoPath, Long restaurantId) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setOnlyLocalConsuption(isOnlyLocalConsuption);
        this.setPhotoPath(photoPath);
        this.setRestaurantId(restaurantId);
    }

    public MenuItemDTO toDTO() {
        return new MenuItemDTO(
                this.name,
                this.description,
                this.price,
                this.isOnlyLocalConsuption,
                this.photoPath,
                this.restaurantId);
    }

    private void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id can't be null");
        }
        this.id = id;
    }

    private void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("The name can't be empty");
        }
        this.name = name;
    }

    private void setDescription(String description) {
        if (description.isBlank()) {
            throw new IllegalArgumentException("The name can't be empty");
        }
        this.description = description;
    }

    private void setPrice(BigDecimal price) {
        if (price.equals(BigDecimal.ZERO)) {
            throw new IllegalArgumentException("The price can't be zero");
        }
        this.price = price;
    }

    private void setOnlyLocalConsuption(boolean isOnlyLocalConsuption) {
        this.isOnlyLocalConsuption = isOnlyLocalConsuption;
    }

    private void setPhotoPath(String photoPath) {
        if (photoPath.isBlank()) {
            throw new IllegalArgumentException("The photoPath can't be empty");
        }
        this.photoPath = photoPath;
    }

    private void setRestaurantId(Long restaurantId) {
        if (restaurantId == null) {
            throw new IllegalArgumentException("The restaurant Id can't be null");
        }
        this.restaurantId = restaurantId;

    }

    public void update(String name, String description, BigDecimal price, boolean isOnlyLocalConsuption,
            String photoPath) {
        this.setName(name);
        this.setDescription(description);
        this.setPrice(price);
        this.setOnlyLocalConsuption(isOnlyLocalConsuption);
        this.setPhotoPath(photoPath);
    }

}
