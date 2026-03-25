package br.com.fiap.tech_challenge_ii.user.core.domain;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Owner extends User {

    private List<Restaurant> restaurants;

    public Owner(Long id, String name, String userType, String email, List<Restaurant> restaurants) {
        super(id, name, email, userType);
        this.restaurants = restaurants;
    }

    public Owner(Long id, String name, String userType, String email) {
        super(id, name, email, userType);
        this.restaurants = new ArrayList<>();
    }

}
