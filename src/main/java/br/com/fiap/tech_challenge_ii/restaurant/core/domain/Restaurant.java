package br.com.fiap.tech_challenge_ii.restaurant.core.domain;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.WeeklySchedule;
import br.com.fiap.tech_challenge_ii.user.core.domain.User;
import lombok.Getter;

@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private KitchenType kitchenType;
    private WeeklySchedule openingHours;
    private User owner; //TODO: check using of User
    //private Menu menu; //TODO: check using of Menu

    private Restaurant(final String name, final Address address, final KitchenType kitchenType, WeeklySchedule openingHours, final User owner) {
        this.name = name;
        this.address = address;
        this.kitchenType = kitchenType;
        this.openingHours = openingHours;
        this.owner = owner;
    }

    public static Restaurant newRestaurant(final String name,
                                           final Address address,
                                           final KitchenType kitchenType,
                                           final WeeklySchedule openingHours,
                                           final User owner) {
        return new Restaurant(name, address, kitchenType, openingHours, owner);
    }

    public void renameTo(String newName) {
        this.name = newName;
    }

    public void updateAddress(Address newAddress) {
        this.address = newAddress;
    }

    public void changeKitchenType(KitchenType newKitchenType) {
        this.kitchenType = newKitchenType;
    }

    public void changeOpeningHours(WeeklySchedule newOpeningHours) {
        this.openingHours = newOpeningHours;
    }

    public void changeOwner(User newOwner) {
        this.owner = newOwner;
    }
}
