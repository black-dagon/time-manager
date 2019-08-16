package org.blackdagon.timemanager.facade.impl;

import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.model.Meeting;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.blackdagon.timemanager.validator.TimeValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

import static org.blackdagon.timemanager.validator.TimeValidator.validate;

@Component("dateTimeCalculationFacade")
public class DefaultDateTimeCalculationFacade implements DateTimeCalculationFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationFacade.class);

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

        try {
            validate(calculatedTime);
        } catch (TimeValidatorException e) {
            LOG.error(e.getMessage());
            return meetings;
        }

        for (Meeting meeting : meetings) {
            try {
                if(!meeting.isEmpty()) {
                    meeting.setTime(validate(appendZeroIfNecessary(meeting.getTime())));
                }
            } catch (TimeValidatorException e) {
                LOG.error(e.getMessage());
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

            int hoursLength = 1;

            if(hh.contains("-")) {
                hoursLength = 2;
            }

            if (hh.length() == hoursLength) {
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
