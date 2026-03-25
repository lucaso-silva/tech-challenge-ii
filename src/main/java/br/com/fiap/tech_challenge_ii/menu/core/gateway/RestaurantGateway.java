package br.com.fiap.tech_challenge_ii.menu.core.gateway;

import java.util.Optional;

import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;

public interface RestaurantGateway {

    Optional<Restaurant> findRestaurantById(Long restaurantId);
}
