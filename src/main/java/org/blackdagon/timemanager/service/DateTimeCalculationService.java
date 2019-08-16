package org.blackdagon.timemanager.service;

import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;

import java.time.LocalTime;

public interface DateTimeCalculationService {

    String calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    String calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings);

    int stringTimeToMinutes(String time);

    String intTimeToString(int time);

    LocalTime getTime(String time);
}
