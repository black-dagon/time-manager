package org.blackdagon.timemanager.facade.impl;

import org.apache.commons.lang3.StringUtils;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.blackdagon.timemanager.service.impl.DefaultDateTimeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("dateTimeCalculationFacade")
public class DefaultDateTimeCalculationFacade implements DateTimeCalculationFacade {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationService.class);

    @Autowired
    private DateTimeCalculationService dateTimeCalculationService;

    @Override
    public String calculateDifferenceInTime(String startTime, String endTime) {
        LocalTime result = dateTimeCalculationService.calculateDifferenceInTime(appendZeroIfNecessary(startTime), appendZeroIfNecessary(endTime));
        return result.toString();
    }

    @Override
    public String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime) {
        LocalTime result = dateTimeCalculationService.calculateDifferenceInTimeWithoutLunch(appendZeroIfNecessary(startTime), appendZeroIfNecessary(endTime));
        return result.toString();
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

            if (hh.length() <= 1) {
                hh = "0" + hh;
            }

            if (mm.length() <= 1) {
                mm = "0" + mm;
            }

            return hh + ":" + mm;
        }

        return hhmm;
    }
}
