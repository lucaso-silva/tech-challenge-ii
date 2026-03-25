package br.com.fiap.tech_challenge_ii.user.infra.gateway.http;

import br.com.fiap.tech_challenge_ii.user.core.domain.Restaurant;
import br.com.fiap.tech_challenge_ii.user.core.gateway.RestaurantGateway;
import br.com.fiap.tech_challenge_ii.user.infra.gateway.http.json.RestaurantJson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@RequestMapping("/user")
public class RestaurantWebFluxGateway implements RestaurantGateway {

	@Override
    @GetMapping("/{loggedInUserId}")
	public List<Restaurant> getByUserId(@PathVariable Long loggedInUserId) {

        return Collections.emptyList();

    }

	private Restaurant map(RestaurantJson restaurantJson) {
		return new Restaurant(restaurantJson.getId(), restaurantJson.getName());
	}

}
