package br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.repository;

import br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.entity.RestaurantUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJPARepository extends JpaRepository<RestaurantUserEntity, Long> {
}
