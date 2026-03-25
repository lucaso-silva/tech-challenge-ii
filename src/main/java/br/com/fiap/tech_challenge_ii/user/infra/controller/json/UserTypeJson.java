package br.com.fiap.tech_challenge_ii.user.infra.controller.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserTypeJson {
	private Long id;

	@JsonProperty("nome")
	private String name;
	
	@JsonProperty("tipo")
	private String type;
}
