package org.blackdagon.timemanager.service.impl;

import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("dateTimeCalculationService")
public class DefaultDateTimeCalculationService implements DateTimeCalculationService {

    public static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationService.class);
    private static final int LUNCH = 30;

    @Override
    public LocalTime calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        LocalTime startTime = getTime(hoursAndMinutesStart);
        LocalTime endTime = getTime(hoursAndMinutesEnd);

        LocalTime result = endTime.minusHours(startTime.getHour());
        result = result.minusMinutes(startTime.getMinute());

        return result;
    }

    @Override
    public LocalTime calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        LocalTime result = calculateDifferenceInTime(hoursAndMinutesStart, hoursAndMinutesEnd);
        result = result.minusMinutes(LUNCH);

        return result;
    }

    @Override
    public LocalTime getTime(String time) {
        return LocalTime.parse(time);
    }
}
