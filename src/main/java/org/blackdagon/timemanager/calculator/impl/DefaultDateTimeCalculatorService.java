package org.blackdagon.timemanager.calculator.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.blackdagon.timemanager.calculator.DateTimeCalculatorService;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;

@Component("dateTimeCalculatorService")
public class DefaultDateTimeCalculatorService implements DateTimeCalculatorService {

    public static Logger LOG = LogManager.getLogger(DefaultDateTimeCalculatorService.class);

    @Override
    public String calculateDiffrenceInDates(String hoursAndMinutesStart, String hoursAndMinutesEnd) {

        if(!validateTime(hoursAndMinutesStart) || !validateTime(hoursAndMinutesEnd))
        {
            return "Time not valid";
        }

        String[] startTimeParsed = hoursAndMinutesStart.split(":");
        String[] endTimeParsed = hoursAndMinutesEnd.split(":");

        LocalDateTime startTime = LocalDateTime.of(2018,1,1, Integer.parseInt(startTimeParsed[0]), Integer.parseInt(startTimeParsed[1]));
        LocalDateTime endTime = LocalDateTime.of(2018,1,1,Integer.parseInt(endTimeParsed[0]), Integer.parseInt(endTimeParsed[1]));

        Duration duration = Duration.between(startTime, endTime);
        long hours = duration.toHours();
        long minutes = duration.minusHours(hours).toMinutes();

        return appendLeadingZero(String.valueOf(hours)) + ":" + appendLeadingZero(String.valueOf(minutes));
    }

    @Override
    public String getTimeWithoutLunch(String hoursAndMinutes) {

        if(!validateTime(hoursAndMinutes))
        {
            return "Time not valid";
        }

        String[] startTimeParsed = hoursAndMinutes.split(":");

        LocalDateTime startTime = LocalDateTime.of(2018,1,1, Integer.parseInt(startTimeParsed[0]), Integer.parseInt(startTimeParsed[1])).minusMinutes(30);

        int hours = startTime.getHour();
        int minutes = startTime.getMinute();

        return appendLeadingZero(String.valueOf(hours)) + ":" + appendLeadingZero(String.valueOf(minutes));
    }

    private String appendLeadingZero(String time){
        if (time.length() == 1){
            return "0" + time;
        }
        return time;
    }

    private boolean validateTime(String time){
        try {
            SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
            parser.parse(time);

            return true;

        } catch (ParseException e) {
            LOG.error(e.getMessage());

            return false;
        }
    }
}
