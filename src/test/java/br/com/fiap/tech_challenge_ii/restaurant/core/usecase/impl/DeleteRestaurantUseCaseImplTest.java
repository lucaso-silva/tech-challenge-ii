package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UnauthorizedOperationException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeleteRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteRestaurantUseCaseImpl deleteRestaurantUseCase;

    @Test
    void delete_shouldDeleteRestaurant_whenUserIsOwner(){

        Restaurant restaurant = RestaurantHelper.buildRestaurant();

        when(restaurantGateway.findById(1L))
                .thenReturn(Optional.of(restaurant));

        deleteRestaurantUseCase.deleteById(1L, 1L);

        verify(restaurantGateway, times(1)).deleteById(1L);
    }

    @Test
    void delete_shouldThrowRestaurantNotFoundException_whenRestaurantNotFound(){

        when(restaurantGateway.findById(2L))
                .thenReturn(Optional.empty());

        RestaurantNotFoundException exception =  assertThrows(
                RestaurantNotFoundException.class,
                () -> deleteRestaurantUseCase.deleteById(1L, 2L)
        );

        assertEquals("Restaurant not found", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(2L);
        verify(restaurantGateway, never()).deleteById(2L);

    }

    @Test
    void delete_shouldThrowUnauthorizedOperationException_whenUserIsNotOwner(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();

        when(restaurantGateway.findById(1L))
                .thenReturn(Optional.of(restaurant));

        UnauthorizedOperationException exception = assertThrows(
                UnauthorizedOperationException.class,
                () -> deleteRestaurantUseCase.deleteById(2L, 1L)
        );

        assertEquals("User not authorized to delete this restaurant", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(1L);
        verify(restaurantGateway, never()).deleteById(1L);
    }
}
