package br.com.fiap.tech_challenge_ii.user.core.gateway;

import br.com.fiap.tech_challenge_ii.user.core.domain.Restaurant;

import java.util.List;



public interface RestaurantGateway {

	List<Restaurant> getByUserId(Long loggedInUserId);

}
