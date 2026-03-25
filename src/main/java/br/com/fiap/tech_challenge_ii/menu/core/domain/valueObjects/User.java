package br.com.fiap.tech_challenge_ii.menu.core.domain.valueObjects;

import lombok.Getter;

@Getter
public class User {

    private Long id;
    private String name;

    public User(Long id, String name) {
        this.setId(id);
        this.setName(name);
    }

    private void setId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("The id can't be null");
        }
        this.id = id;

    }

    private void setName(String name) {
        if (name.isBlank()) {
            throw new IllegalArgumentException("The name can't be empty");
        }
        this.name = name;

    }

}
