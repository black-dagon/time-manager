package org.blackdagon.timemanager.service.impl;

import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.blackdagon.timemanager.model.Meeting;
import org.blackdagon.timemanager.service.DateTimeCalculationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.DateTimeException;
import java.time.LocalTime;

import static org.blackdagon.timemanager.constants.TimeManagerConstants.INVALID_TIME_MESSAGE;
import static org.blackdagon.timemanager.constants.TimeManagerConstants.LUNCH_MINUTES;

@Component("dateTimeCalculationService")
public class DefaultDateTimeCalculationService implements DateTimeCalculationService {

    public static final Logger LOG = LoggerFactory.getLogger(DefaultDateTimeCalculationService.class);

    @Override
    public LocalTime calculateDifferenceInTime(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        LocalTime startTime = getTime(hoursAndMinutesStart);
        LocalTime endTime = getTime(hoursAndMinutesEnd);

        LocalTime result = endTime.minusHours(startTime.getHour());
        result = result.minusMinutes(startTime.getMinute());

        return result;
    }

    @Override
    public LocalTime calculateDifferenceInTimeWithoutLunch(String hoursAndMinutesStart, String hoursAndMinutesEnd) {
        LocalTime result = calculateDifferenceInTime(hoursAndMinutesStart, hoursAndMinutesEnd);
        result = result.minusMinutes(LUNCH_MINUTES);

        return result;
    }

    @Override
    public ObservableList<Meeting> calculateTimeDifferenceInColumns(String calculatedTime, ObservableList<Meeting> meetings) {
        LocalTime calculated = getTime(calculatedTime);

        for (Meeting meeting : meetings) {
            try {
                if(!StringUtils.isBlank(meeting.getTime())) {
                    LocalTime meetingTime = getTime(meeting.getTime());
                    calculated = calculated.minusHours(meetingTime.getHour());
                    calculated = calculated.minusMinutes(meetingTime.getMinute());
                    meeting.setInJira(calculated.toString());
                }
            } catch (DateTimeException e) {
                LOG.error(INVALID_TIME_MESSAGE);
            }
        }
        return meetings;
    }

    @Override
    public LocalTime getTime(String time) {
        return LocalTime.parse(time);
    }
}
