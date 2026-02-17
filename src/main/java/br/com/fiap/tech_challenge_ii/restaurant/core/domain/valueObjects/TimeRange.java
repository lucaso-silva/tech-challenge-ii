package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

import java.time.LocalTime;

public record TimeRange(LocalTime openingHour, LocalTime closingHour) {
}

