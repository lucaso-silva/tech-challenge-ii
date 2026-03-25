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
import java.util.List;
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
import br.com.fiap.tech_challenge_ii.menu.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UnauthorizedException;
import br.com.fiap.tech_challenge_ii.menu.core.exception.UserNotFoundException;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.MenuItemGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.menu.core.gateway.UserGateway;

@ExtendWith(MockitoExtension.class)
class CreateMenuItemUseCaseImplTest {

    @Mock
    private MenuItemGateway menuItemGateway;

    @Mock
    private RestaurantGateway restaurantGateway;

    @Mock
    private UserGateway userGateway;

    @InjectMocks
    private CreateMenuItemUseCaseImpl createMenuItemUseCase;

    @Test
    void save_shouldCreateMenuItem_whenItemDoesNotExistAndUserIsOwner() {
        MenuItemDTO dto = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem savedMenuItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByMenuItemNameAndRestaurantId(eq("Burger"), eq(1L)))
                .thenReturn(Optional.empty());
        when(menuItemGateway.save(any(MenuItem.class))).thenReturn(savedMenuItem);

        List<Long> result = createMenuItemUseCase.save(List.of(dto), 1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0));
        verify(menuItemGateway, times(1)).save(any(MenuItem.class));
    }

    @Test
    void save_shouldThrowUnauthorizedException_whenUserIsNotOwner() {
        MenuItemDTO dto = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(999L, "Other User");

        when(userGateway.findUserById(999L)).thenReturn(Optional.of(user));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));

        UnauthorizedException exception = assertThrows(
                UnauthorizedException.class,
                () -> createMenuItemUseCase.save(List.of(dto), 999L));

        assertEquals("Action not allowed", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void save_shouldThrowUserNotFoundException_whenUserDoesNotExist() {
        MenuItemDTO dto = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(999L)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> createMenuItemUseCase.save(List.of(dto), 999L));

        assertEquals("User not found", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
        verify(restaurantGateway, never()).findRestaurantById(any());
    }

    @Test
    void save_shouldThrowExistingMenuItemException_whenItemAlreadyExists() {
        MenuItemDTO dto = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem existingItem = new MenuItem(
                1L,
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByMenuItemNameAndRestaurantId(eq("Burger"), eq(1L)))
                .thenReturn(Optional.of(existingItem));

        ExistingMenuItemException exception = assertThrows(
                ExistingMenuItemException.class,
                () -> createMenuItemUseCase.save(List.of(dto), 1L));

        assertEquals("Item with this name already exists", exception.getMessage());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void save_shouldCreateMultipleMenuItems_whenListHasMultipleValidItems() {
        MenuItemDTO dto1 = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                1L);

        MenuItemDTO dto2 = new MenuItemDTO(
                "Pizza",
                "Cheesy pizza",
                new BigDecimal("45.90"),
                false,
                "/photos/pizza.jpg",
                1L);

        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", 1L);
        User user = new User(1L, "Test User");

        MenuItem savedItem1 = new MenuItem(1L, "Burger", "Delicious burger",
                new BigDecimal("25.90"), false, "/photos/burger.jpg", 1L);
        MenuItem savedItem2 = new MenuItem(2L, "Pizza", "Cheesy pizza",
                new BigDecimal("45.90"), false, "/photos/pizza.jpg", 1L);

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(restaurantGateway.findRestaurantById(1L)).thenReturn(Optional.of(restaurant));
        when(menuItemGateway.findByMenuItemNameAndRestaurantId(eq("Burger"), eq(1L)))
                .thenReturn(Optional.empty());
        when(menuItemGateway.findByMenuItemNameAndRestaurantId(eq("Pizza"), eq(1L)))
                .thenReturn(Optional.empty());
        when(menuItemGateway.save(any(MenuItem.class)))
                .thenReturn(savedItem1)
                .thenReturn(savedItem2);

        List<Long> result = createMenuItemUseCase.save(List.of(dto1, dto2), 1L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0));
        assertEquals(2L, result.get(1));
        verify(menuItemGateway, times(2)).save(any(MenuItem.class));
    }

    @Test
    void save_shouldReturnEmptyList_whenInputListIsEmpty() {
        List<Long> result = createMenuItemUseCase.save(List.of(), 1L);

        assertEquals(0, result.size());
        verify(userGateway, never()).findUserById(any());
        verify(restaurantGateway, never()).findRestaurantById(any());
        verify(menuItemGateway, never()).findByMenuItemNameAndRestaurantId(any(), any());
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }

    @Test
    void save_shouldThrowRestaurantNotFoundException_whenRestaurantNotFound() {
        MenuItemDTO dto = new MenuItemDTO(
                "Burger",
                "Delicious burger",
                new BigDecimal("25.90"),
                false,
                "/photos/burger.jpg",
                999L);

        User user = new User(1L, "Test User");

        when(userGateway.findUserById(1L)).thenReturn(Optional.of(user));
        when(restaurantGateway.findRestaurantById(999L)).thenReturn(Optional.empty());

        assertThrows(RestaurantNotFoundException.class,
                () -> createMenuItemUseCase.save(List.of(dto), 1L));
        verify(menuItemGateway, never()).save(any(MenuItem.class));
    }
}
