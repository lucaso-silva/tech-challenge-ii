package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.KitchenType;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.GetRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetRestaurantByIdUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private GetRestaurantByIdUseCaseImpl getRestaurantByIdUseCase;

    @Test
    void getRestaurantById_shouldReturnRestaurant_whenRestaurantExists() {

        Restaurant restaurant = RestaurantHelper.buildRestaurant();

        when(restaurantGateway.findById(1L))
                .thenReturn(Optional.of(restaurant));

        GetRestaurantOutput output = getRestaurantByIdUseCase.getById(1L);

        assertNotNull(output);
        assertEquals(1L, output.id());
        assertEquals("Bean Pizza", output.name());
        assertEquals(KitchenType.ITALIAN, output.kitchenType());

        verify(restaurantGateway, times(1)).findById(1L);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void getRestaurantById_shouldThrowRestaurantNotFoundException_whenRestaurantDoesNotExist() {
        when(restaurantGateway.findById(999L))
                .thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> getRestaurantByIdUseCase.getById(999L)
        );

        assertEquals("Restaurant not found", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(999L);
        verifyNoMoreInteractions(restaurantGateway);
    }
}
