package br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects;

import lombok.Getter;

@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Long ownerId;

    public Restaurant(Long id, String name, Long ownerId) {
        this.setId(id);
        this.setName(name);
        this.setOwnerId(ownerId);
    }

    public boolean isOwnedBy(Long userId) {
        return this.ownerId == userId;
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

    private void setOwnerId(Long ownerId) {
        if (ownerId == null) {
            throw new IllegalArgumentException("The ownerId can't be null");
        }
        this.ownerId = ownerId;
    }

}
