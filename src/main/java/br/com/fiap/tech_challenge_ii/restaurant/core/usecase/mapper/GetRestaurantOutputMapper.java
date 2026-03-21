package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.AddressDTO;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.GetRestaurantOutput;

public class GetRestaurantOutputMapper {
    public static GetRestaurantOutput from(final Restaurant restaurant) {
        AddressDTO addressDTO = new AddressDTO(restaurant.getAddress().id(),
                restaurant.getAddress().street(),
                restaurant.getAddress().number(),
                restaurant.getAddress().neighborhood(),
                restaurant.getAddress().city(),
                restaurant.getAddress().zipCode());

        return new GetRestaurantOutput(restaurant.getId(),
                restaurant.getName(),
                addressDTO,
                restaurant.getKitchenType(),
                restaurant.getOpeningHours()
        );
    }
}
