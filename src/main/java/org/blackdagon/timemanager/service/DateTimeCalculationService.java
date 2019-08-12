package org.blackdagon.timemanager.service;

import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;

import java.time.LocalTime;

public interface DateTimeCalculationService {

    LocalTime calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    LocalTime calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd);

    ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings);

    LocalTime getTime(String time);
}
