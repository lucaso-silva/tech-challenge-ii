package br.com.fiap.tech_challenge_ii.restaurant.core.gateway;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.filter.RestaurantSearchFilter;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    Restaurant create(Restaurant restaurant);

    List<Restaurant> findAll(RestaurantSearchFilter filter);

    Optional<Restaurant> findById(Long id);

    Restaurant update(Restaurant restaurant);

    void deleteById(Long id);
}
