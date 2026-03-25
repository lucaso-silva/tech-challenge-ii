package br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.tech_challenge_ii.menu.infra.gateway.db.entity.MenuUserEntity;

public interface UserRepository extends JpaRepository<MenuUserEntity, Long> {

}
