package org.blackdagon.timemanager.facade.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    void calculateDifferenceInTime() {
        String result = dateTimeCalculationFacade.calculateDifferenceInTime(startTime, endTime);
        assertThat(result, is("01:15"));
    }

    @Test
    void calculateDifferenceInTimeWithoutLunch() {
        String result = dateTimeCalculationFacade.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        assertThat(result, is("00:45"));
    }

    @Test
    void getTimeForJira() {
        String startTime = "08:00";
        startTime = dateTimeCalculationFacade.getTimeForJira(startTime);
        assertThat(startTime, is("8h 0m"));
    }


    @Test
    void appendZeroIfNecessary() {
        String startTime = "8:0";
        startTime = dateTimeCalculationFacade.appendZeroIfNecessary(startTime);
        assertThat(startTime, is("08:00"));
    }

    @Test
    void appendZeroIfNecessaryNoColon() {
        String startTime = "80";
        startTime = dateTimeCalculationFacade.appendZeroIfNecessary(startTime);
        assertThat(startTime, is("80"));
    }

}