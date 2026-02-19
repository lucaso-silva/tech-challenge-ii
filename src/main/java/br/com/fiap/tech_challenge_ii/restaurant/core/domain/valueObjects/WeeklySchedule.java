package br.com.fiap.tech_challenge_ii.restaurant.core.domain.valueObjects;

import br.com.fiap.tech_challenge_ii.restaurant.core.exception.DomainException;

import java.time.DayOfWeek;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeeklySchedule {
    private final Map<DayOfWeek, BusinessHours> weeklyOpeningTimes;

    private WeeklySchedule(Map<DayOfWeek, BusinessHours> weeklyOpeningTimes) {
        this.weeklyOpeningTimes = Collections.unmodifiableMap(weeklyOpeningTimes);
    }

    public static WeeklySchedule of(List<DailySchedule> dailySchedules) {
        if(dailySchedules == null || dailySchedules.isEmpty())
            throw new DomainException("The restaurant must have at least one opening day");

        Map<DayOfWeek, BusinessHours> newWeeklyOpeningTimes = new HashMap<>();

        for(DailySchedule dailySchedule : dailySchedules) {
            if(dailySchedule.dayOfWeek() == null)
                throw new DomainException("Day of week must be informed");

            if(dailySchedule.businessHours() == null)
                throw new DomainException("Business hours must be informed");

            if(newWeeklyOpeningTimes.containsKey(dailySchedule.dayOfWeek()))
                throw new DomainException("Opening time for %s already defined".formatted(dailySchedule.dayOfWeek()));

            newWeeklyOpeningTimes.put(dailySchedule.dayOfWeek(), dailySchedule.businessHours());
        }

        return new WeeklySchedule(newWeeklyOpeningTimes);
    }

    public Map<DayOfWeek, BusinessHours> getWeeklyOpeningTimes() {
        return Collections.unmodifiableMap(weeklyOpeningTimes);
    }
}
