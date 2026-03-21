package br.com.fiap.tech_challenge_ii.restaurant.infra.controller.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.dto.*;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.*;
import org.springframework.stereotype.Component;

@Component
public class RestaurantControllerMapper {

    public CreateRestaurantInput toInput(CreateRestaurantRequest request) {
        var addressInput = request.address() == null
                ? null
                : toAddressInput(request.address()) ;

        return new CreateRestaurantInput(request.name(),
                addressInput,
                request.kitchenType(),
                request.openingHours());
    }

    public UpdateRestaurantInput toInput(UpdateRestaurantRequest request) {
        var addressInput = request.address() == null
                ? null
                : toAddressInput(request.address());

        return new UpdateRestaurantInput(request.name(),
                addressInput,
                request.kitchenType(),
                request.openingHours());
    }

    public CreateRestaurantResponse toResponse(CreateRestaurantOutput restaurant) {
        return new CreateRestaurantResponse(restaurant.id(),
                restaurant.name(),
                restaurant.kitchenType());
    }

    public GetRestaurantResponse toResponse(GetRestaurantOutput restaurant) {
        return new GetRestaurantResponse(restaurant.id(),
                restaurant.name(),
                toAddressResponse(restaurant.addressDTO()),
                restaurant.kitchenType(),
                restaurant.openingHours());
    }

    public ListRestaurantResponse toResponse(ListRestaurantOutput restaurant) {
        return new ListRestaurantResponse(restaurant.id(),
                restaurant.name(),
                restaurant.kitchenType());
    }

    public UpdateRestaurantResponse toResponse(UpdateRestaurantOutput updatedRestaurant) {
        return new UpdateRestaurantResponse(updatedRestaurant.id(),
                updatedRestaurant.name(),
                toAddressResponse(updatedRestaurant.address()),
                updatedRestaurant.kitchenType(),
                updatedRestaurant.openingHours());
    }

    private AddressDTO toAddressInput(AddressJson addressJson){
        if(addressJson == null) return null;

        return new AddressDTO(addressJson.id(),
                addressJson.street(),
                addressJson.number(),
                addressJson.neighborhood(),
                addressJson.city(),
                addressJson.zipCode());
    }

    private AddressJson toAddressResponse(AddressDTO addressDTO){
        return new AddressJson(addressDTO.id(),
                addressDTO.street(),
                addressDTO.number(),
                addressDTO.neighborhood(),
                addressDTO.city(),
                addressDTO.zipCode());
    }
}
