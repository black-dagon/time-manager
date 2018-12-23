package org.blackdagon.timemanager.service;

import java.time.LocalTime;

public interface DateTimeCalculationService {

    LocalTime calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    LocalTime calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    LocalTime getTime(String time);
}
