package org.blackdagon.timemanager.facade;

import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;

public interface DateTimeCalculationFacade {

    String calculateDifferenceInTime(String startTime, String endTime);

    String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime);

    ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings);

    String getTimeForJira(String time);

    String appendZeroIfNecessary(String hhmm);
}