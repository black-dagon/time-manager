package org.blackdagon.timemanager.service.impl;

import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.blackdagon.timemanager.model.Meeting;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.blackdagon.timemanager.validator.TimeValidatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.blackdagon.timemanager.constants.TimeManagerConstants.LUNCH_MINUTES;
import static org.blackdagon.timemanager.validator.TimeValidator.validate;

@Component("dateTimeCalculationService")
public class DefaultDateTimeCalculationService implements DateTimeCalculationService {

    public static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationService.class);

    @Override
    public String calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        int start = stringTimeToMinutes(hoursAndMinutesStart);
        int stop = stringTimeToMinutes(hoursAndMinutesEnd);

        int result = Math.subtractExact(stop, start);

        return longTimeToString(result);
    }

    @Override
    public String calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        int start = stringTimeToMinutes(hoursAndMinutesStart);
        int stop = stringTimeToMinutes(hoursAndMinutesEnd);

        int result = Math.subtractExact(stop, start + LUNCH_MINUTES);

        return longTimeToString(result);
    }

    @Override
    public ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings) {
        int calculated = stringTimeToMinutes(calculatedTime);

        for (Meeting meeting : meetings) {
            try {
                if(!StringUtils.isBlank(meeting.getTime())) {
                    int meetingTime = stringTimeToMinutes(validate(meeting.getTime()));
                    calculated = Math.subtractExact(calculated, meetingTime);
                    meeting.setInJira(longTimeToString(calculated));

                }
            } catch (TimeValidatorException e) {
                // Skip calculation
            }
        }
        return meetings;
    }

    @Override
    public int stringTimeToMinutes(String time) {
        int all = 0;

        String[] hhMM = time.split(":");
        int hours = Integer.parseInt(hhMM[0]);
        int minutes = Integer.parseInt(hhMM[1]);

        all = Math.addExact(all, minutes);
        all = Math.addExact(all, (Math.multiplyExact(60, hours)));

        return all;
    }

    @Override
    public String longTimeToString(int time) {
        BigDecimal bigDecimal = BigDecimal.valueOf(time);
        bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_DOWN);
        bigDecimal = bigDecimal.divide(BigDecimal.valueOf(60L), BigDecimal.ROUND_DOWN);
        int mmModulus = Math.floorMod(60, time);

        String hh = bigDecimal.toString();
        String mm;

        if(mmModulus == 0) {
            mm = String.valueOf(time).replace("-", "");
        }else {
            mm = String.valueOf(Math.floorMod(time, 60)).replace("-", "");
        }

        if(hh.length() == 1) {
            hh = "0" + hh;
        }

        if(mm.length() == 1) {
            mm = "0" + mm;
        }

        if(time < 0) {
            hh = "-" + hh;
        }

        return hh + ":" + mm;
    }


    @Override
    public LocalTime getTime(String time) {
        return LocalTime.parse(time);
    }
}
