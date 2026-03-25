package br.com.fiap.tech_challenge_ii.restaurant.infra.gateway.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "app_user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantUserEntity {
    @Id
    Long id;
    @Column(name = "user_type", nullable = false)
    String userType;
}
