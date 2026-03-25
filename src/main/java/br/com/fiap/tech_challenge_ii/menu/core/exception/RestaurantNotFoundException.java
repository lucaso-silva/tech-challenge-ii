package br.com.fiap.tech_challenge_ii.menu.core.exception;

public class RestaurantNotFoundException extends NotFoundException {

    private static final String CODE = "restaurant.restaurantNotFound";
    private static final String MESSAGE = "Restaurant not found";

    public RestaurantNotFoundException() {
        super(CODE, MESSAGE);
    }

}
