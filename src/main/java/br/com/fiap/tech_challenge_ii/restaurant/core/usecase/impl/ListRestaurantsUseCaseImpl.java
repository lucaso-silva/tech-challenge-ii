package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.ListRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.filter.RestaurantSearchFilter;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.ListRestaurantsUseCase;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper.ListRestaurantOutputMapper;

import java.util.List;

public class ListRestaurantsUseCaseImpl implements ListRestaurantsUseCase {
    private final RestaurantGateway restaurantGateway;

    public ListRestaurantsUseCaseImpl(RestaurantGateway restaurantGateway) {this.restaurantGateway = restaurantGateway;}

    @Override
    public List<ListRestaurantOutput> list(RestaurantSearchFilter filter) {
        List<Restaurant> restaurants = restaurantGateway.findAll(filter);

        return restaurants.stream()
                .map(ListRestaurantOutputMapper::from)
                .toList();
    }
}
