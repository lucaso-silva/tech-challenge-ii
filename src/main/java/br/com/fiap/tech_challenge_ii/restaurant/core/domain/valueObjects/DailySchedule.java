package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

import java.time.DayOfWeek;

public record DailySchedule(
        DayOfWeek dayOfWeek,
        BusinessHours businessHours
) {
}
