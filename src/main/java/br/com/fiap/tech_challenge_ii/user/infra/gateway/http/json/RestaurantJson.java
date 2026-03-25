package br.com.fiap.tech_challenge_ii.user.infra.gateway.http.json;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantJson {
	private Long id;
	private String name;
}
