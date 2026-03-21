package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.CreateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UnauthorizedOperationException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.UserGateway;
import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateRestaurantUseCaseImpl createRestaurant;

    @Test
    void create_shouldCreateRestaurant_whenUserTypeIsOwner() {

        CreateRestaurantInput json = RestaurantHelper.buildCreateRestaurantInput();

        User userOwner = RestaurantHelper.buildOwner();

        Restaurant restaurant = RestaurantHelper.buildRestaurant();

        when(userGateway.getUserById(1L))
                .thenReturn(Optional.of(userOwner));

        when(restaurantGateway.create(any(Restaurant.class)))
                .thenReturn(restaurant);

        var output = createRestaurant.create(1L, json);

        assertNotNull(output);
        assertAll(
                () -> assertEquals("Bean Pizza", output.name()),
                () -> assertEquals("ITALIAN", output.kitchenType())
        );

        verify(userGateway, times(1)).getUserById(1L);
        verify(restaurantGateway, times(1)).create(any());

    }

    @Test
    void create_shouldThrowUserNotFoundException_whenUserNotFound(){
        CreateRestaurantInput json = RestaurantHelper.buildCreateRestaurantInput();

        when(userGateway.getUserById(2L))
                .thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> createRestaurant.create(2L, json)
        );

        assertEquals("There is no user with id %s".formatted(2L), exception.getMessage());

        verify(userGateway, times(1)).getUserById(2L);
        verify(restaurantGateway, never()).create(any(Restaurant.class));
    }

    @Test
    void create_shouldThrowUnauthorizedOperationException_whenUserTypeIsNotOwner() {
        CreateRestaurantInput json = RestaurantHelper.buildCreateRestaurantInput();

        User userCustomer = RestaurantHelper.buildCustomer();

        when(userGateway.getUserById(2L))
                .thenReturn(Optional.of(userCustomer));

        UnauthorizedOperationException exception =  assertThrows(
                UnauthorizedOperationException.class,
                () -> createRestaurant.create(2L, json)
        );

        assertEquals("Only users with OWNER role can create restaurants", exception.getMessage());

        verify(userGateway, times(1)).getUserById(2L);
        verify(restaurantGateway, never()).create(any(Restaurant.class));

    }
}
