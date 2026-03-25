package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.impl;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.KitchenType;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.User;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantInput;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.UpdateRestaurantOutput;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.RestaurantNotFoundException;
import br.com.fiap.tech_challenge_ii.restaurant.core.exception.UnauthorizedOperationException;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.helper.RestaurantHelper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateRestaurantUseCaseImplTest {

    @Mock
    private RestaurantGateway restaurantGateway;

    @InjectMocks
    private UpdateRestaurantUseCaseImpl updateRestaurantUseCase;

    @Test
    void update_shouldUpdateRestaurant_whenDataIsValidAndUserIsOwner(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();
        UpdateRestaurantInput input = RestaurantHelper.buildUpdateRestaurantInput();
        Restaurant updatedRestaurant = RestaurantHelper.buildUpdatedRestaurant();
        Long restaurantId = restaurant.getId();
        Long ownerId = restaurant.getOwnerId();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(restaurantGateway.update(any(Restaurant.class)))
                .thenReturn(updatedRestaurant);

        var output = updateRestaurantUseCase.update(ownerId, restaurantId, input);

        assertNotNull(output);
        assertAll(
                () -> assertEquals(restaurantId, output.id()),
                () -> assertEquals("updated-name", output.name()),
                () -> assertEquals("updated-street-name", output.address().street()),
                () -> assertEquals("updated-number", output.address().number()),
                () -> assertEquals("updated-city", output.address().city()),
                () -> assertEquals(KitchenType.BRAZILIAN, output.kitchenType()),
                () -> assertEquals("updated-openingHours", output.openingHours())
        );

        verify(restaurantGateway, times(1)).findById(restaurantId);

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantGateway, times(1)).update(captor.capture());

        Restaurant capturedRestaurant = captor.getValue();
        assertAll(
                () -> assertEquals("updated-name", capturedRestaurant.getName()),
                () -> assertEquals("updated-street-name", capturedRestaurant.getAddress().street()),
                () -> assertEquals("updated-city", capturedRestaurant.getAddress().city()),
                () -> assertEquals(KitchenType.BRAZILIAN, capturedRestaurant.getKitchenType())
        );

        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void update_shouldThrowRestaurantNotFoundException_whenRestaurantNotFound(){
        Long restaurantId = 999L;
        UpdateRestaurantInput input = RestaurantHelper.buildUpdateRestaurantInput();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.empty());

        RestaurantNotFoundException exception = assertThrows(
                RestaurantNotFoundException.class,
                () -> updateRestaurantUseCase.update(1L, restaurantId, input)
        );

        assertEquals("Restaurant not found", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(restaurantGateway, never()).update(any(Restaurant.class));
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void update_shouldThrowUnauthorizedOperationException_whenUserIsNotOwner(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();
        UpdateRestaurantInput input = RestaurantHelper.buildUpdateRestaurantInput();
        User customer = RestaurantHelper.buildCustomer();
        Long restaurantId = restaurant.getId();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        UnauthorizedOperationException exception = assertThrows(
                UnauthorizedOperationException.class,
                () -> updateRestaurantUseCase.update(customer.id(), restaurant.getId(), input)
        );

        assertEquals("User not authorized to update this restaurant", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(restaurantGateway, never()).update(any(Restaurant.class));
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void update_shouldUpdateOnlyName_whenOnlyNameIsProvided(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();
        UpdateRestaurantInput partialUpdateInput = new UpdateRestaurantInput("updated-name",
                null, null, null);
        Restaurant restaurantUpdatedName = Restaurant.newRestaurant(restaurant.getId(),
                "updated-name",
                restaurant.getAddress(),
                restaurant.getKitchenType().name(),
                restaurant.getOpeningHours(),
                restaurant.getOwnerId());
        Long restaurantId = restaurant.getId();
        Long ownerId = restaurant.getOwnerId();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(restaurantGateway.update(any(Restaurant.class)))
                .thenReturn(restaurantUpdatedName);

        var output = updateRestaurantUseCase.update(ownerId, restaurantId, partialUpdateInput);

        assertNotNull(output);
        assertAll(
                () -> assertEquals(restaurantId, output.id()),
                () -> assertEquals("updated-name", output.name()),
                () -> assertEquals(restaurant.getAddress().street(), output.address().street()),
                () -> assertEquals(restaurant.getAddress().city(), output.address().city()),
                () -> assertEquals(restaurant.getKitchenType(), output.kitchenType()),
                () -> assertEquals(restaurant.getOpeningHours(), output.openingHours())
        );

        verify(restaurantGateway, times(1)).findById(restaurantId);

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantGateway, times(1)).update(captor.capture());

        Restaurant capturedRestaurant = captor.getValue();
        assertAll(
                () -> assertEquals("updated-name", capturedRestaurant.getName()),
                () -> assertEquals(restaurant.getAddress().street(), capturedRestaurant.getAddress().street()),
                () -> assertEquals(restaurant.getKitchenType(), capturedRestaurant.getKitchenType()),
                () -> assertEquals(restaurant.getOpeningHours(), capturedRestaurant.getOpeningHours())
        );
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void update_shouldKeepExistingAddress_whenAddressIsNull(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();
        UpdateRestaurantInput partialUpdateInput = new UpdateRestaurantInput("updated-name",
                null,
                "BRAZILIAN",
                "updated-openingHours");
        Restaurant partialUpdatedRestaurant = Restaurant.newRestaurant(restaurant.getId(),
                partialUpdateInput.name(),
                restaurant.getAddress(),
                partialUpdateInput.kitchenType(),
                partialUpdateInput.openingHours(),
                restaurant.getOwnerId());
        Long restaurantId = restaurant.getId();
        Long ownerId = restaurant.getOwnerId();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        when(restaurantGateway.update(any(Restaurant.class)))
                .thenReturn(partialUpdatedRestaurant);

        UpdateRestaurantOutput output = updateRestaurantUseCase.update(ownerId, restaurantId, partialUpdateInput);

        assertNotNull(output);
        assertAll(
                () -> assertEquals(restaurant.getId(), output.id()),
                () -> assertEquals("updated-name", output.name()),
                () -> assertEquals(restaurant.getAddress().street(), output.address().street()),
                () -> assertEquals(restaurant.getAddress().number(), output.address().number()),
                () -> assertEquals(restaurant.getAddress().neighborhood(), output.address().neighborhood()),
                () -> assertEquals(KitchenType.BRAZILIAN, output.kitchenType()),
                () -> assertEquals("updated-openingHours", output.openingHours())
        );

        verify(restaurantGateway, times(1)).findById(restaurantId);

        ArgumentCaptor<Restaurant> captor = ArgumentCaptor.forClass(Restaurant.class);
        verify(restaurantGateway, times(1)).update(captor.capture());

        Restaurant capturedRestaurant = captor.getValue();
        assertAll(
                () -> assertEquals(restaurant.getId(), capturedRestaurant.getId()),
                () -> assertEquals("updated-name", capturedRestaurant.getName()),
                () -> assertEquals(restaurant.getAddress().street(), capturedRestaurant.getAddress().street()),
                () -> assertEquals(restaurant.getAddress().number(), capturedRestaurant.getAddress().number()),
                () -> assertEquals(restaurant.getAddress().neighborhood(), capturedRestaurant.getAddress().neighborhood()),
                () -> assertEquals(KitchenType.BRAZILIAN, capturedRestaurant.getKitchenType()),
                () -> assertEquals("updated-openingHours", capturedRestaurant.getOpeningHours())
        );
        verifyNoMoreInteractions(restaurantGateway);
    }

    @Test
    void update_shouldThrowDomainException_whenKitchenTypeIsInvalid(){
        Restaurant restaurant = RestaurantHelper.buildRestaurant();
        UpdateRestaurantInput updateKitchenTypeInput = new UpdateRestaurantInput(
                null,
                null,
                "Invalid-kitchen-type",
                null
        );
        Long restaurantId = restaurant.getId();
        Long ownerId = restaurant.getOwnerId();

        when(restaurantGateway.findById(restaurantId))
                .thenReturn(Optional.of(restaurant));

        DomainException exception = assertThrows(
                DomainException.class,
                () -> updateRestaurantUseCase.update(ownerId, restaurantId, updateKitchenTypeInput)
        );

        assertEquals("Invalid kitchen type value: Invalid-kitchen-type", exception.getMessage());

        verify(restaurantGateway, times(1)).findById(restaurantId);
        verify(restaurantGateway, never()).update(any(Restaurant.class));
        verifyNoMoreInteractions(restaurantGateway);
    }
}
