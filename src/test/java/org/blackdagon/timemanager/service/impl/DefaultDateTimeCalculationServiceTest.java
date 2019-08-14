package org.blackdagon.timemanager.service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalTime;
import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultDateTimeCalculationServiceTest {

    @Autowired
    private DefaultDateTimeCalculationService dateTimeCalculationService;

    private String startTime;
    private String endTime;

    @BeforeEach
    void setUp() {
        startTime = "10:45";
        endTime = "12:00";
    }

    @Test
    void calculateDifferenceInTime() {
        String result = dateTimeCalculationService.calculateDifferenceInTime(startTime, endTime);
        assertThat(result, is("01:15"));
    }

    @Test
    void calculateDifferenceInTimeWithoutLunch() {
        String result = dateTimeCalculationService.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        assertThat(result, is("00:45"));
    }

    @Test
    void getTime() {
        LocalTime result = dateTimeCalculationService.getTime(startTime);
        assertThat(result.getHour(), is(10));
        assertThat(result.getMinute(), is(45));
    }

    @Test
    void calculateTimeDifferenceInColumns() {
        String calculatedTime = "10:30";

        ObservableList<Meeting> meetings = FXCollections.observableList(new ArrayList<>());
        Meeting meeting1 = new Meeting();
        meeting1.setTime("00:10");
        Meeting meeting2 = new Meeting();
        meeting2.setTime("00:15");
        Meeting meeting3 = new Meeting();
        meeting3.setTime("10:10");

        meetings.addAll(meeting1, meeting2, meeting3);

        dateTimeCalculationService.calculateTimeDifferenceInColumns(calculatedTime, meetings);

        assertThat(meetings.get(0).getInJira(), is("10:20"));
        assertThat(meetings.get(1).getInJira(), is("10:05"));
        assertThat(meetings.get(2).getInJira(), is("-00:05"));
    }
}
