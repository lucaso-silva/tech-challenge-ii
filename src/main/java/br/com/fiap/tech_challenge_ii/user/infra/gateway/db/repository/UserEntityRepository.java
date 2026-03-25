package br.com.fiap.tech_challenge_ii.user.infra.gateway.db.repository;

import br.com.fiap.tech_challenge_ii.user.infra.gateway.db.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserEntityRepository extends JpaRepository<UserEntity, Long> {

    Boolean existsByEmail(String email);

}
