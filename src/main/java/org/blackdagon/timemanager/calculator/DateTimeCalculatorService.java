package org.blackdagon.timemanager.calculator;

public interface DateTimeCalculatorService {

    String calculateDiffrenceInDates(String hoursAndMinutesStart, String hoursAndMinutesEnd);
    String getTimeWithoutLunch(String hoursAndMinutes);
}
