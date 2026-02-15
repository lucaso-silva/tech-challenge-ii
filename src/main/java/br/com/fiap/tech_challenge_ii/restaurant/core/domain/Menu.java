package br.com.fiap.tech_challenge_ii.core.domain.entities.restaurant;

import java.util.List;

import lombok.Getter;

@Getter
public class Menu {

    private List<MenuItem> itens;

    public Menu(List<MenuItem> itens) {
        this.itens = itens;
    }

}
