package org.blackdagon.timemanager.facade.impl;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.facade.TimeMessageFacade;
import org.blackdagon.timemanager.validator.TimeValidatorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.blackdagon.timemanager.constants.TimeManagerConstants.INVALID_TIME_MESSAGE;

@Component("timeMessageFacade")
public class DefaultTimeMessageFacade implements TimeMessageFacade {

    @Autowired
    private DateTimeCalculationFacade dateTimeCalculationFacade;

    @Override
    public Pair<String, String> getTimeMessages(String startTime, String endTime) {
        String timeWithLunch;
        String timeWithoutLunch;

        try {
            timeWithLunch = dateTimeCalculationFacade.calculateDifferenceInTime(startTime, endTime);
            timeWithoutLunch = dateTimeCalculationFacade.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        } catch (TimeValidatorException e){
            timeWithLunch = INVALID_TIME_MESSAGE;
            timeWithoutLunch = INVALID_TIME_MESSAGE;
        }
        return new ImmutablePair<>(timeWithLunch, timeWithoutLunch);
    }

    @Override
    public String getTimeMessageForJira(String time) {
        return dateTimeCalculationFacade.getTimeForJira(time);
    }
}
