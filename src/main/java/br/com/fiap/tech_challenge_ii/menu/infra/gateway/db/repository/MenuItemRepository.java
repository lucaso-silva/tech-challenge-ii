package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuItemEntity;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItemEntity, Long> {

    List<MenuItemEntity> findByRestaurantId(Long restaurantId);

    Optional<MenuItemEntity> findByNameAndRestaurantId(String name, Long restaurantId);

    Optional<MenuItemEntity> findByNameAndRestaurantIdAndIdNot(String name, Long restaurantId, Long excludeId);

}
