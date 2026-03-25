package br.com.fiap.tech_challenge_ii.restaurant.core.usecase;

public interface DeleteRestaurantUseCase {
    void deleteById(Long userId, Long restaurantId);
}
