package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.AddressDTO;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantOutput;

public class UpdateRestaurantOutputMapper {
    public static UpdateRestaurantOutput from(Restaurant restaurant) {
        AddressDTO addressDTO = new AddressDTO(restaurant.getAddress().id(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().neighborhood(),
                restaurant.getAddress().city(),
                restaurant.getAddress().zipCode());

        return new UpdateRestaurantOutput(restaurant.getId(),
                restaurant.getName(),
                addressDTO,
                restaurant.getKitchenType(),
                restaurant.getOpeningHours());
    }
}
