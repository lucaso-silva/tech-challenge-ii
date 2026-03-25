package br.com.fiap.tech_challenge_ii.restaurant.helper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.AddressDTO;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.AddressJson;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.CreateRestaurantRequest;
import br.com.fiap.tech_challenge_ii.restaurant.infra.controller.json.UpdateRestaurantRequest;

import java.util.List;

import static br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.Address.newAddress;

public class RestaurantHelper {
    public static CreateRestaurantInput buildCreateRestaurantInput(){
        return new CreateRestaurantInput(
                "Bean Pizza",
                new AddressDTO(
                        1L,
                        "Av. Rosa e Silva",
                        "1000",
                        "Aflitos",
                        "Recife",
                        "50000-000"
                ),
                "Italian",
                "seg-sex: 16:00-22:59, sab-dom: 11:00-21:59"
        );
    }

    public static Restaurant buildRestaurant(){
        return Restaurant.newRestaurant(
                1L,
                "Bean Pizza",
                newAddress(1L,
                        "Av. Rosa e Silva",
                        "1000",
                        "Aflitos",
                        "Recife",
                        "50000-000"
                ),
                "Italian",
                "seg-sex: 16:00-22:59, sab-dom: 11:00-21:59",
                1L
        );
    }

    public static List<Restaurant> buildListOfRestaurants(){
        Restaurant restaurant1 = Restaurant.newRestaurant(
                1L,
                "Bean Pizza",
                newAddress(1L,
                        "Av. Rosa e Silva",
                        "1000",
                        "Aflitos",
                        "Recife",
                        "50000-000"
                ),
                "Italian",
                "seg-sex: 16:00-22:59, sab-dom: 11:00-21:59",
                1L
        );

        Restaurant restaurant2 = Restaurant.newRestaurant(
                2L,
                "Java Cafe",
                newAddress(1L,
                        "Av. Rosa e Silva",
                        "1150",
                        "Aflitos",
                        "Recife",
                        "50000-000"
                ),
                "Brazilian",
                "seg-sex: 07:00-20:59, sab-dom: 8:00-20:59",
                1L
        );
        return List.of(restaurant1, restaurant2);
    }

    public static UpdateRestaurantInput buildUpdateRestaurantInput() {
        return new UpdateRestaurantInput(
                "updated-name",
                new AddressDTO(
                        2L,
                        "updated-street-name",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-zip-code"
                ),
                "BRAZILIAN",
                "updated-openingHours"
        );
    }

    public static Restaurant buildUpdatedRestaurant() {
        return Restaurant.newRestaurant(
                1L,
                "updated-name",
                Address.newAddress(
                        2L,
                        "updated-street-name",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-zip-code"
                ),
                "BRAZILIAN",
                "updated-openingHours",
                1L
        );
    }

    public static User buildOwner(){
        return new User(1L,"owner");
    }

    public static User buildCustomer(){
        return new User(2L,"customer");
    }

    public static CreateRestaurantRequest buildCreateRestaurantRequest() {
        return new CreateRestaurantRequest("any-restaurant-name",
                new AddressJson(null,
                        "any-street-name",
                        "any-number",
                        "any-neighborhood",
                        "any-city",
                        "any-zip-code"),
                "Italian",
                "any-opening-hours"
        );
    }

    public static UpdateRestaurantRequest buildUpdateRestaurantRequest() {
        return new UpdateRestaurantRequest(
                "updated-restaurant-name",
                new AddressJson(
                        null,
                        "updated-street",
                        "updated-number",
                        "updated-neighborhood",
                        "updated-city",
                        "updated-zip-code"
                ),
                "Mexican",
                "updated-opening-hours"
        );
    }
}
