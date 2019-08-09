package org.blackdagon.timemanager.facade.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.facade.TimeMessageFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeParseException;

@Component("timeMessageFacade")
public class DefaultTimeMessageFacade implements TimeMessageFacade {

    private static final String PARSE_EXCEPTION_MESSAGE = "Time not valid";

    @Autowired
    private DateTimeCalculationFacade dateTimeCalculationFacade;

    @Override
    public Pair<String, String> getTimeMessages(String startTime, String endTime) {
        String timeWithLunch;
        String timeWithoutLunch;

        try {
            timeWithLunch = dateTimeCalculationFacade.calculateDifferenceInTime(startTime, endTime);
            timeWithoutLunch = dateTimeCalculationFacade.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        } catch (DateTimeParseException e){
            timeWithLunch = PARSE_EXCEPTION_MESSAGE;
            timeWithoutLunch = PARSE_EXCEPTION_MESSAGE;
        }
        return new ImmutablePair<>(timeWithLunch, timeWithoutLunch);
    }

    @Override
    public String getTimeMessageForJira(String time) {
        return dateTimeCalculationFacade.getTimeForJira(time);
    }
}
