package org.blackdagon.timemanager.facade.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.blackdagon.timemanager.model.Meeting;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultDateTimeCalculationFacadeTest {

    @Autowired
    private DefaultDateTimeCalculationFacade dateTimeCalculationFacade;

    private String startTime;
    private String endTime;

    @BeforeEach
    void setUp() {
        startTime = "10:45";
        endTime = "12:00";
    }

    @Test
    void calculateDifferenceInTime() throws Exception {
        String result = dateTimeCalculationFacade.calculateDifferenceInTime(startTime, endTime);
        assertThat(result, is("01:15"));
    }

    @Test
    void calculateDifferenceInTimeWithoutLunch() throws Exception {
        String result = dateTimeCalculationFacade.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        assertThat(result, is("00:45"));
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
        meeting3.setTime("00:40");

        meetings.addAll(meeting1, meeting2, meeting3);

        dateTimeCalculationFacade.calculateTimeDifferenceInColumns(calculatedTime, meetings);

        assertThat(meetings.get(0).getInJira(), is("10:20"));
        assertThat(meetings.get(1).getInJira(), is("10:05"));
        assertThat(meetings.get(2).getInJira(), is("09:25"));
    }


    @Test
    void getTimeForJira() {
        String startTime = "08:00";
        startTime = dateTimeCalculationFacade.getTimeForJira(startTime);
        assertThat(startTime, is("8h 0m"));
    }
}
