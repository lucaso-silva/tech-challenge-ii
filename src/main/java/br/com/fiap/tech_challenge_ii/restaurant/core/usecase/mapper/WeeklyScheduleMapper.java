package br.com.fiap.tech_challenge_ii.restaurant.core.usecase.mapper;

import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.BusinessHours;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.DailySchedule;
import br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects.WeeklySchedule;
import br.com.fiap.tech_challenge_ii.restaurant.core.dto.WeeklyScheduleDTO;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

public class WeeklyScheduleMapper {

    public static WeeklySchedule from(WeeklyScheduleDTO weeklyScheduleDTO) {
        List<DailySchedule> dailySchedules = weeklyScheduleDTO.getDailySchedules().stream()
                .map(day -> new DailySchedule(
                        DayOfWeek.valueOf(day.dayOfWeek().trim().toUpperCase()),
                        BusinessHours.of(
                                LocalTime.parse(day.openingHour()),
                                LocalTime.parse(day.closingHour())
                        )
                ))
                .toList();

        return WeeklySchedule.of(dailySchedules);
    }
}
