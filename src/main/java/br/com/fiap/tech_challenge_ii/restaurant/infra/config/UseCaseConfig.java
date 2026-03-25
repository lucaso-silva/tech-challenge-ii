package br.com.fiap.tech_challenge_ii.restaurant.infra.config;

import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.*;
import br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreateRestaurantUseCase createRestaurant(RestaurantGateway gateway, UserGateway userGateway) {
        return new CreateRestaurantUseCaseImpl(gateway, userGateway);
    }

    @Bean
    public GetRestaurantByIdUseCase getRestaurantById(RestaurantGateway gateway) {
        return new GetRestaurantByIdUseCaseImpl(gateway);
    }

    @Bean
    public ListRestaurantsUseCase listRestaurants(RestaurantGateway gateway) {
        return new ListRestaurantsUseCaseImpl(gateway);
    }

    @Bean
    public DeleteRestaurantUseCase deleteRestaurantById(RestaurantGateway gateway) {
        return new DeleteRestaurantUseCaseImpl(gateway);
    }

    @Bean
    public UpdateRestaurantUseCase updateRestaurant(RestaurantGateway gateway) {
        return new UpdateRestaurantUseCaseImpl(gateway);
    }

}
