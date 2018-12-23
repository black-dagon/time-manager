package org.blackdagon.timemanager.facade.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.blackdagon.timemanager.facade.DateTimeCalculationFacade;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.blackdagon.timemanager.service.impl.DefaultDateTimeCalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component("dateTimeCalculationFacade")
public class DefaultDateTimeCalculationFacade implements DateTimeCalculationFacade {

    private static final Logger LOG = LogManager.getLogger(DefaultDateTimeCalculationService.class);

    @Autowired
    private DateTimeCalculationService dateTimeCalculationService;

    @Override
    public String calculateDifferenceInTime(String startTime, String endTime) {
        LocalTime result = dateTimeCalculationService.calculateDifferenceInTime(startTime, endTime);
        return result.toString();
    }

    @Override
    public String calculateDifferenceInTimeWithoutLunch(String startTime, String endTime) {
        LocalTime result = dateTimeCalculationService.calculateDifferenceInTimeWithoutLunch(startTime, endTime);
        return result.toString();
    }
}
