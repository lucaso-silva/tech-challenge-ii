package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UnauthorizedOperationException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.UpdateRestaurantUseCase;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper.UpdateRestaurantOutputMapper;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateRestaurantUseCaseImpl implements UpdateRestaurantUseCase {
    private final RestaurantGateway restaurantGateway;

    @Override
    public UpdateRestaurantOutput update(Long userId, Long restaurantId, UpdateRestaurantInput input) {
        var restaurant = restaurantGateway.findById(restaurantId)
                .orElseThrow(RestaurantNotFoundException::new);

        if(!restaurant.getOwnerId().equals(userId)) {
            throw new UnauthorizedOperationException("User not authorized to update this restaurant");
        }

        var address = input.address() == null ? restaurant.getAddress() :
                Address.newAddress(restaurant.getAddress().id(),
                input.address().street(),
                input.address().number(),
                input.address().neighborhood(),
                input.address().city(),
                input.address().zipCode());

        restaurant.update(input.name(), address, input.kitchenType(), input.openingHours());
        var updatedRestaurant = restaurantGateway.update(restaurant);

        return UpdateRestaurantOutputMapper.from(updatedRestaurant);
    }
}
