package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.KitchenType;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.WeeklySchedule;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.CreateRestaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper.WeeklyScheduleMapper;

public class CreateRestaurantImpl implements CreateRestaurant {
    private final RestaurantGateway restaurantGateway;

    public CreateRestaurantImpl(RestaurantGateway restaurantGateway) {
        this.restaurantGateway = restaurantGateway;
    }

    @Override
    public CreateRestaurantOutput create(CreateRestaurantInput restaurantInput) {
        var newAddress = Address.newAddress(restaurantInput.address().street(),
                restaurantInput.address().number(),
                restaurantInput.address().neighborhood(),
                restaurantInput.address().city(),
                restaurantInput.address().zipCode());

        var kitchenType = KitchenType.valueOf(restaurantInput.kitchenType().trim().toUpperCase());

        WeeklySchedule weekSchedule = WeeklyScheduleMapper.from(restaurantInput.weeklyScheduleDTO());

        //TODO: validate user type owner

        var newRestaurant = Restaurant.newRestaurant(restaurantInput.name(),
                newAddress,
                kitchenType,
                weekSchedule,
                restaurantInput.ownerId());

        var restaurant = restaurantGateway.create(newRestaurant);
        return CreateRestaurantOutput.from(restaurant);
    }
}
