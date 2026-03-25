package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.fiap.tech_challenge_ii.menu.core.domain.MenuItem;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.Restaurant;
import br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.menu.core.exception.MenuItemNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UnauthorizedException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;

@ExtendWith(MockitoExtension.class)
class DeleteMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private DeleteMenuItemUseCaseImpl deleteMenuItemUseCase;

    @Test
    void delete_shouldDeleteMenuItem_whenUserIsOwner() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem menuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        deleteMenuItemUseCase.delete(1L, 1L);

        verify(menuItemGateway, times(1)).delete(menuItem);
    }

    @Test
    void delete_shouldThrowUnauthorizedException_whenUserIsNotOwner() {
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(999L, "Other User");

        MenuItem menuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(999L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        assertThrows(
                UnauthorizedException.class,
                () -> deleteMenuItemUseCase.delete(1L, 999L));

        verify(menuItemGateway, never()).delete(any(MenuItem.class));
    }

    @Test
    void delete_shouldThrowMenuItemNotFoundException_whenItemNotFound() {
        User user = new User(1L, "Test User");

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(999L)).thenReturn(Optional.empty());

        assertThrows(
                MenuItemNotFoundException.class,
                () -> deleteMenuItemUseCase.delete(999L, 1L));

        verify(menuItemGateway, never()).delete(any(MenuItem.class));
    }

    @Test
    void delete_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        when(userGateway.findUserById(999L)).thenReturn(Optional.empty());

        assertThrows(
                UserNotFoundException.class,
                () -> deleteMenuItemUseCase.delete(1L, 999L));

        verify(menuItemGateway, never()).findById(any());
        verify(restaurantGateway, never()).findRestaurantById(any());
        verify(menuItemGateway, never()).delete(any(MenuItem.class));
    }

    @Test
    void delete_shouldThrowRestaurantNotFoundException_whenRestaurantNotFound() {
        User user = new User(1L, "Test User");

        MenuItem menuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(menuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.empty());

        assertThrows(
                RestaurantNotFoundException.class,
                () -> deleteMenuItemUseCase.delete(1L, 1L));

        verify(menuItemGateway, never()).delete(any(MenuItem.class));
    }
}
