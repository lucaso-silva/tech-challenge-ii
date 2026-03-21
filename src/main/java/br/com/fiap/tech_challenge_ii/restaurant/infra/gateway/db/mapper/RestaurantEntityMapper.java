package br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.entity.AddressEntity;
import br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.entity.RestaurantEntity;
import org.springframework.stereotype.Component;

@Component
public class RestaurantEntityMapper {
    public RestaurantEntity toEntity(Restaurant restaurant) {
        if (restaurant == null) {
            return null;
        }

        var restaurantEntity = new RestaurantEntity();

        restaurantEntity.setId(restaurant.getId());
        restaurantEntity.setName(restaurant.getName());
        restaurantEntity.setOpeningHours(restaurant.getOpeningHours());
        restaurantEntity.setAddress(toEntity(restaurant.getAddress()));
        restaurantEntity.setKitchenType(restaurant.getKitchenType().name());
        restaurantEntity.setOwnerId(restaurant.getOwnerId());

        return restaurantEntity;
    }

    public Restaurant toDomain(RestaurantEntity restaurantEntity) {
        if (restaurantEntity == null) {
            return null;
        }

        return Restaurant.newRestaurant(restaurantEntity.getId(),
                restaurantEntity.getName(),
                toDomain(restaurantEntity.getAddress()),
                restaurantEntity.getKitchenType(),
                restaurantEntity.getOpeningHours(),
                restaurantEntity.getOwnerId());
    }

    private static AddressEntity toEntity(Address address) {
        if (address == null) {
            return null;
        }
        var addressEntity = new AddressEntity();
        if(address.id() != null) {
            addressEntity.setId(address.id());
        }
        addressEntity.setStreet(address.street());
        addressEntity.setNumber(address.number());
        addressEntity.setNeighborhood(address.neighborhood());
        addressEntity.setCity(address.city());
        addressEntity.setZipCode(address.zipCode());

        return addressEntity;
    }

    private static Address toDomain(AddressEntity addressEntity) {
        if (addressEntity == null) {
            return null;
        }
        return new Address(addressEntity.getId(),
                addressEntity.getStreet(),
                addressEntity.getNumber(),
                addressEntity.getNeighborhood(),
                addressEntity.getCity(),
                addressEntity.getZipCode());
    }
}
