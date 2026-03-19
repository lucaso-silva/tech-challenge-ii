package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;

@ExtendWith(MockitoExtension.class)
class FindMenuItemUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private MenuItemGateway menuItemGateway;

    @InjectMocks
    private FindMenuItemUseCaseImpl findMenuItemUseCase;

    @Test
    void findByRestaurantId_shouldReturnMenuItemDTOList_whenRestaurantExists() {
        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurant 1",
                1L);

        MenuItem menuItem1 = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                restaurantId);

        MenuItem menuItem2 = new MenuItem(
                2L,
                "Pizza",
                "Cheesy pizza",
                new BigDecimal("45.90"),
                false,
                "/photos/pizza.jpg",
                restaurantId);

        when(restaurantGateway.findRestaurantById(restaurantId))
                .thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(restaurantId))
                .thenReturn(List.of(menuItem1, menuItem2));

        List<MenuItemDTO> result = findMenuItemUseCase.findByRestaurantId(restaurantId);

        assertEquals(2, result.size());
        assertEquals("Burger", result.get(0).name());
        assertEquals("Pizza", result.get(1).name());
        verify(restaurantGateway, times(1)).findRestaurantById(restaurantId);
        verify(menuItemGateway, times(1)).findByRestaurantId(restaurantId);
    }

    @Test
    void findByRestaurantId_shouldThrowRestaurantNotFoundException_whenRestaurantDoesNotExist() {
        Long restaurantId = 999L;

        when(restaurantGateway.findRestaurantById(restaurantId))
                .thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> findMenuItemUseCase.findByRestaurantId(restaurantId));

        assertEquals("Restaurant not found", exception.getMessage());
        verify(restaurantGateway, times(1)).findRestaurantById(restaurantId);
        verify(menuItemGateway, times(0)).findByRestaurantId(any());
    }

    @Test
    void findByRestaurantId_shouldReturnEmptyList_whenRestaurantExistsButHasNoItems() {
        Long restaurantId = 1L;

        Restaurant restaurant = new Restaurant(
                restaurantId,
                "Restaurant 1",
                1L);

        when(restaurantGateway.findRestaurantById(restaurantId))
                .thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByRestaurantId(restaurantId))
                .thenReturn(List.of());

        List<MenuItemDTO> result = findMenuItemUseCase.findByRestaurantId(restaurantId);

        assertEquals(0, result.size());
        verify(restaurantGateway, times(1)).findRestaurantById(restaurantId);
        verify(menuItemGateway, times(1)).findByRestaurantId(restaurantId);
    }
}
