package br.com.fiap.tech_challenge_ii.restaurant.core.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class WeeklyScheduleDTO {
    private List<DailyScheduleDTO> dailySchedules;
}
