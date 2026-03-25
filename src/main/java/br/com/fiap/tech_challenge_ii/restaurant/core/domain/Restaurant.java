package br.com.fiap.tech_challenge_ii.restaurant.core.domain;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;
import lombok.Getter;

@Getter
public class Restaurant {

    private final Long id;
    private String name;
    private Address address;
    private KitchenType kitchenType;
    private String openingHours;
    private Long ownerId;

    private Restaurant(final Long id, final String name, final Address address, final String kitchenType, String openingHours, final Long ownerId) {
        this.id = id;
        renameTo(name);
        updateAddress(address);
        changeKitchenType(kitchenType);
        changeOpeningHours(openingHours);
        changeOwner(ownerId);
    }

    public static Restaurant newRestaurant(final Long id,
                                           final String name,
                                           final Address address,
                                           final String kitchenType,
                                           final String openingHours,
                                           final Long ownerId) {

        return new Restaurant(id, name, address, kitchenType, openingHours, ownerId);
    }

    public void update(String name, Address address, String kitchenType, String openingHours) {
        if(name != null){
            renameTo(name);
        }
        if(address != null){
            updateAddress(address);
        }
        if(kitchenType != null){
            changeKitchenType(kitchenType);
        }
        if(openingHours != null){
            changeOpeningHours(openingHours);
        }
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

    private void changeKitchenType(String newKitchenType) {
        if(newKitchenType == null || newKitchenType.isBlank())
            throw new DomainException("You need to inform the restaurant kitchen type!");

        this.kitchenType = KitchenType.from(newKitchenType);
    }

    private void changeOpeningHours(String newOpeningHours) {
        if(newOpeningHours == null || newOpeningHours.isBlank())
            throw new DomainException("You need to inform the restaurant opening hours!");

        this.openingHours = newOpeningHours;
    }

    private void changeOwner(Long newOwnerId) {
        if(newOwnerId == null)
            throw new DomainException("You need to inform the restaurant owner!");

        this.ownerId = newOwnerId;
    }
}
