package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

public record DailyScheduleDTO(
        String dayOfWeek,
        String openingHour,
        String closingHour
) {
}
