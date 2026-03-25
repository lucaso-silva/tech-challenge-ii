package br.com.fiap.tech_challenge_ii.restaurant.core.domain;

import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;

public enum KitchenType {
    ITALIAN,
    JAPANESE,
    BRAZILIAN,
    MEXICAN,
    CHINESE,
    AMERICAN,
    INDIAN,
    THAI,
    FRENCH,
    GREEK;

    public static KitchenType from(String kitchenType){
        if(kitchenType == null || kitchenType.isBlank()){
            throw new DomainException("Kitchen Type cannot be empty");
        }

        try{
            return KitchenType.valueOf(kitchenType.trim().toUpperCase());
        }catch(IllegalArgumentException e){
            throw new DomainException("Invalid kitchen type value: " + kitchenType);
        }
    }
}
