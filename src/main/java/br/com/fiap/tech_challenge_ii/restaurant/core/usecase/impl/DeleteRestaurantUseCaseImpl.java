package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UnauthorizedOperationException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.DeleteRestaurantUseCase;

public class DeleteRestaurantUseCaseImpl implements DeleteRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    public DeleteRestaurantUseCaseImpl(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public void deleteById(Long userId, Long restaurantId) {
        var restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        if(!restaurant.getOwnerId().equals(userId)) {
            throw new UnauthorizedOperationException("User not authorized to delete this restaurant");
        }
        restaurantGateway.deleteById(restaurantId);
    }
}
