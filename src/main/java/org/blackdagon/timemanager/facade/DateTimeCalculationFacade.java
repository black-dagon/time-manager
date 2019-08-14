package org.blackdagon.timemanager.facade;

import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;
import org.blackdagon.timemanager.validator.TimeValidatorException;

public interface DateTimeCalculationFacade {

    String calculateDifferenceInTime(String startTime, String endTime) throws TimeValidatorException;

    String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime) throws TimeValidatorException;

    ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings);

    String appendZeroIfNecessary(String hhmm);

    String getTimeForJira(String time);
}
