package org.blackdagon.timemanager.facade.impl;

import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.model.Meeting;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.blackdagon.timemanager.service.impl.DefaultDateTimeCalculationService;
import org.blackdagon.timemanager.validator.TimeValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static org.blackdagon.timemanager.constants.TimeManagerConstants.INVALID_TIME_MESSAGE;
import static org.blackdagon.timemanager.validator.TimeValidator.validate;

@Component("dateTimeCalculationFacade")
public class DefaultDateTimeCalculationFacade implements DateTimeCalculationFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationService.class);

    @Autowired
    private DateTimeCalculationService dateTimeCalculationService;

    @Override
    public String calculateDifferenceInTime(String startTime, String endTime) throws TimeValidatorException {
        startTime = appendZeroIfNecessary(startTime);
        endTime = appendZeroIfNecessary(endTime);
        return dateTimeCalculationService.calculateDifferenceInTime(validate(startTime), validate(endTime));
    }

    @Override
    public String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime) throws TimeValidatorException {
        startTime = appendZeroIfNecessary(startTime);
        endTime = appendZeroIfNecessary(endTime);

        return dateTimeCalculationService.calculateDifferenceInTimeWithoutLunch(validate(startTime), validate(endTime));
    }

    @Override
    public ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings) {
        for(Meeting meeting : meetings) {
            try {
                meeting.setTime(validate(meeting.getTime()));
            } catch (TimeValidatorException e) {
                LOG.error(INVALID_TIME_MESSAGE);
            }
        }

        dateTimeCalculationService.calculateTimeDifferenceInColumns(calculatedTime, meetings);

        return meetings;
    }

    @Override
    public String getTimeForJira(String time) {
        LocalTime formattedTime = dateTimeCalculationService.getTime(time);
        return formattedTime.getHour() + "h " + formattedTime.getMinute() + "m";
    }

    @Override
    public String appendZeroIfNecessary(String hhmm){

        if(StringUtils.contains(hhmm, ":")) {
            String[] formattedString = hhmm.split(":");
            String hh = formattedString[0];
            String mm = formattedString[1];

            if (hh.length() == 1) {
                hh = "0" + hh;
            }

            if (mm.length() == 1) {
                mm = "0" + mm;
            }

            return hh + ":" + mm;
        }

        return hhmm;
    }

}
