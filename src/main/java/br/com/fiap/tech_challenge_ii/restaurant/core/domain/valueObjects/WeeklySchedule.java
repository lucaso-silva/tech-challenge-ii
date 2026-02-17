package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

import lombok.Getter;

import java.time.DayOfWeek;
import java.util.Map;

@Getter
public class WeeklySchedule {
    private Map<DayOfWeek, TimeRange> openingHours;
}
