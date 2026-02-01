package br.com.fiap.tech_challenge_ii.core.domain.entities.restaurant;

import br.com.fiap.tech_challenge_ii.core.domain.valueObjects.Address;
import lombok.Getter;

@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private KitchenTypeEnum kitchenType;
    private String openingHours;
    private Menu menu;

    public Restaurant(Long id, String name, Address address, KitchenTypeEnum kitchenType, String openingHours,
            Menu menu) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
        this.menu = menu;
    }

}
