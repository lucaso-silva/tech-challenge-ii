package br.com.fiap.tech_challenge_ii.menu.core.usecase.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import br.com.fiap.tech_challenge_ii.menu.core.dto.MenuItemDTO;
import br.com.fiap.tech_challenge_ii.menu.core.exception.ExistingMenuItemException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.MenuItemNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UnauthorizedException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;

@ExtendWith(MockitoExtension.class)
class UpdateMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private UserGateway userGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private UpdateMenuItemUseCaseImpl updateMenuItemUseCase;

    @Test
    void update_shouldUpdateMenuItem_whenValidDataAndUserIsOwner() {
        MenuItemDTO dto = new MenuItemDTO(
                "Updated Burger",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem existingMenuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        MenuItem updatedMenuItem = new MenuItem(
                1L,
                "Updated Burger",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByMenuItemNameAndRestaurantIdExcludingId(eq("Updated Burger"), eq(1L), eq(1L)))
                .thenReturn(Optional.empty());
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(updatedMenuItem);

        MenuItemDTO result = updateMenuItemUseCase.update(1L, dto, 1L);

        assertEquals("Updated Burger", result.name());
        assertEquals("Delicious updated burger", result.description());
        assertEquals(new BigDecimal("29.90"), result.price());
        verify(menuItemGateway, times(1)).save(any(MenuItem.class));
    }

    @Test
    void update_shouldThrowUnauthorizedException_whenUserIsNotOwner() {
        MenuItemDTO dto = new MenuItemDTO(
                "Updated Burger",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(999L, "Other User");

        MenuItem existingMenuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(999L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> updateMenuItemUseCase.update(1L, dto, 999L));

        assertEquals("Action not allowed", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void update_shouldThrowMenuItemNotFoundException_whenItemNotFound() {
        MenuItemDTO dto = new MenuItemDTO(
                "Updated Burger",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        User user = new User(1L, "Test User");

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(999L)).thenReturn(Optional.empty());

        MenuItemNotFoundException exception = assertThrows(
                MenuItemNotFoundException.class,
                () -> updateMenuItemUseCase.update(999L, dto, 1L));

        assertEquals("Menu Item not found", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void update_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        MenuItemDTO dto = new MenuItemDTO(
                "Updated Burger",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        when(userGateway.findUserById(999L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> updateMenuItemUseCase.update(1L, dto, 999L));

        assertEquals("User not found", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void update_shouldThrowExistingMenuItemException_whenDuplicateName() {
        MenuItemDTO dto = new MenuItemDTO(
                "Existing Item",
                "Delicious updated burger",
                new BigDecimal("29.90"),
                false,
                "/photos/updated-burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem existingMenuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        MenuItem duplicateItem = new MenuItem(
                2L,
                "Existing Item",
                "Another item",
                new BigDecimal("15.90"),
                false,
                "/photos/existing.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(menuItemGateway.findById(1L)).thenReturn(Optional.of(existingMenuItem));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByMenuItemNameAndRestaurantIdExcludingId(eq("Existing Item"), eq(1L), eq(1L)))
                .thenReturn(Optional.of(duplicateItem));

        ExistingMenuItemException exception = assertThrows(
                ExistingMenuItemException.class,
                () -> updateMenuItemUseCase.update(1L, dto, 1L));

        assertEquals("Item with this name already exists", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

}
