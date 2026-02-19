package br.com.fiap.tech_challenge_ii.user.core.domain;

import java.time.LocalDateTime;
import java.util.List;

import br.com.fiap.tech_challenge_ii.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;

public class Owner extends User {

    List<Restaurant> restaurants;

    public Owner(Long id, String name, String email, String login, String password, LocalDateTime lastModifiedDate,
            Address address, List<Restaurant> restaurants) {
        super(id, name, email, login, password, lastModifiedDate, address);
        this.restaurants = restaurants;
    }

    public boolean isRestaurantOwner(Long restaurantId) {
        return restaurants.stream().anyMatch(r -> r.getId().equals(restaurantId));
    }

}
