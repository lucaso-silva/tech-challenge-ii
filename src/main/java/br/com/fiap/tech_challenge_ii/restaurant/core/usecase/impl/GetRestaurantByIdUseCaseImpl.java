package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.dto.GetRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.GetRestaurantByIdUseCase;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper.GetRestaurantOutputMapper;

public class GetRestaurantByIdUseCaseImpl implements GetRestaurantByIdUseCase {
    private final RestaurantGateway restaurantGateway;

    public GetRestaurantByIdUseCaseImpl(RestaurantGateway restaurantGateway) {this.restaurantGateway = restaurantGateway;}

    @Override
    public GetRestaurantOutput getById(Long id) {
        var restaurantById = restaurantGateway.findById(id)
                .orElseThrow(RestaurantNotFoundException::new);

        return GetRestaurantOutputMapper.from(restaurantById);
    }
}
