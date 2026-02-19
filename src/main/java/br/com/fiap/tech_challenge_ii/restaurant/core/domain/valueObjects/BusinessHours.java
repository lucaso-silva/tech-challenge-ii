package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;

import java.time.LocalTime;

public record BusinessHours(LocalTime opensAt, LocalTime closesAt) {

    public static BusinessHours of(LocalTime opensAt, LocalTime closesAt) {
        if(opensAt == null || closesAt == null)
            throw new DomainException("Opening and closing hours must be provided");

        if(!opensAt.isBefore(closesAt))
            throw new DomainException("Opening hours cannot be after closing hours");

        return new BusinessHours(opensAt, closesAt);
    }

}

