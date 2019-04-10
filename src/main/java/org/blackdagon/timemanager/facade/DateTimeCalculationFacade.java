package org.blackdagon.timemanager.facade;

public interface DateTimeCalculationFacade {

    String calculateDifferenceInTime(String startTime, String endTime);

    String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime);

    String getTimeForJira(String time);

    String appendZeroIfNecessary(String hhmm);
}