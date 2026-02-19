package br.com.fiap.tech_challenge_ii.restaurant.core.domain;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.WeeklySchedule;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;
import lombok.Getter;

@Getter
public class Restaurant {

    private Long id;
    private String name;
    private Address address;
    private KitchenType kitchenType;
    private WeeklySchedule openingHours;
    private Long ownerId; //TODO: check using of User
    //private Long menuId; //TODO: check using of Menu

    private Restaurant(final String name, final Address address, final KitchenType kitchenType, WeeklySchedule openingHours, final Long ownerId) {
        renameTo(name);
        updateAddress(address);
        changeKitchenType(kitchenType);
        changeOpeningHours(openingHours);
        changeOwner(ownerId);
    }

    public static Restaurant newRestaurant(final String name,
                                           final Address address,
                                           final KitchenType kitchenType,
                                           final WeeklySchedule openingHours,
                                           final Long ownerId) {

        return new Restaurant(name, address, kitchenType, openingHours, ownerId);
    }

    private void renameTo(String newName) {
        if(newName == null || newName.isBlank())
            throw new DomainException("You need to inform the restaurant name!");

        this.name = newName;
    }

    private void updateAddress(Address newAddress) {
        if(newAddress == null)
            throw new DomainException("You need to inform the restaurant address!");

        this.address = newAddress;
    }

    private void changeKitchenType(KitchenType newKitchenType) {
        if(newKitchenType == null)
            throw new DomainException("You need to inform the restaurant kitchen type!");

        this.kitchenType = newKitchenType;
    }

    private void changeOpeningHours(WeeklySchedule businessHours) {
        if(businessHours == null)
            throw new DomainException("You need to inform the restaurant opening hours!");

        this.openingHours = businessHours;
    }

    private void changeOwner(Long newOwnerId) {
        if(newOwnerId == null)
            throw new DomainException("You need to inform the restaurant owner!");

        this.ownerId = newOwnerId;
    }
}
