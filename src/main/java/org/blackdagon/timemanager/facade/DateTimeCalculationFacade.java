package org.blackdagon.timemanager.facade;

public interface DateTimeCalculationFacade {

    String calculateDifferenceInTime(String startTime, String endTime);

    String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime);
}