package br.com.fiap.tech_challenge_ii.restaurant.core.gateway;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantGateway {
    Restaurant create(Restaurant restaurant);

    List<Restaurant> findAll();

    List<Restaurant> findByNameLike(String name);

    Optional<Restaurant> findById(Long id);

    Restaurant update(Restaurant restaurant);

    void deleteById(Long id);

}
