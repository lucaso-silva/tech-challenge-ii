package br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.repository;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.restaurant.core.gateway.filter.RestaurantSearchFilter;
import br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.mapper.RestaurantEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class RestaurantGatewayImpl implements RestaurantGateway {

    private final RestaurantJPARepository restaurantRepository;
    private final RestaurantEntityMapper mapper;

    @Override
    public Restaurant create(Restaurant restaurant) {
        var restaurantEntity = restaurantRepository.save(mapper.toEntity(restaurant));

        return mapper.toDomain(restaurantEntity);
    }

    @Override
    public List<Restaurant> findAll(RestaurantSearchFilter filter) {
        return restaurantRepository.findByNameLikeIgnoreCase("%" + filter.name() + "%").stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Restaurant> findById(Long id) {
        return restaurantRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Restaurant update(Restaurant restaurant) {
        var restaurantUpdated = restaurantRepository.save(mapper.toEntity(restaurant));

        return mapper.toDomain(restaurantUpdated);
    }

    @Override
    public void deleteById(Long id) {
        restaurantRepository.deleteById(id);
    }
}
