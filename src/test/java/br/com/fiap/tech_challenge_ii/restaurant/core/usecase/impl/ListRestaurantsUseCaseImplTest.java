package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.ListRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.filter.RestaurantSearchFilter;
import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListRestaurantsUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private ListRestaurantsUseCaseImpl listRestaurantsUseCase;

    @Test
    void list_shouldReturnAListOfRestaurants_whenThereAreRestaurants() {
        List<Restaurant> restaurants = RestaurantHelper.buildListOfRestaurants();

        RestaurantSearchFilter filter = new RestaurantSearchFilter("");

        when(restaurantGateway.findAll(filter))
                .thenReturn(restaurants);

        List<ListRestaurantOutput> output = listRestaurantsUseCase.list(filter);

        assertNotNull(output);
        assertEquals(restaurants.size(), output.size());

        assertEquals(1L, output.get(0).id());
        assertEquals("Bean Pizza", output.get(0).name());
        assertEquals("ITALIAN", output.get(0).kitchenType());

        assertEquals(2L, output.get(1).id());
        assertEquals("Java Cafe", output.get(1).name());
        assertEquals("BRAZILIAN", output.get(1).kitchenType());

        verify(restaurantGateway, times(1)).findAll(filter);
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void list_shouldReturnEmptyList_whenThereAreNoRestaurants() {
        RestaurantSearchFilter filter = new RestaurantSearchFilter("");

        when(restaurantGateway.findAll(filter))
                .thenReturn(List.of());

        List<ListRestaurantOutput> output = listRestaurantsUseCase.list(filter);

        assertNotNull(output);
        assertTrue(output.isEmpty());

        verify(restaurantGateway, times(1)).findAll(filter);
        verifyNoMoreInteractions(restaurantGateway);
    }
}
