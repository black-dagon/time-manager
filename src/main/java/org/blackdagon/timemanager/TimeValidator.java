package org.blackdagon.timemanager;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TimeValidator {

    private String message;
    private Boolean valid;

    public void validate(String hoursAndMinutesStart, String hoursAndMinutesEnd) {

        try {
            LocalTime.parse(hoursAndMinutesStart);
            LocalTime.parse(hoursAndMinutesEnd);

            valid = true;

        } catch (DateTimeParseException e) {
            valid = false;
            message = "Time not valid";
        }
    }

    public boolean validate(String time) {

        try {
            LocalTime.parse(time);

            return true;

        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public String getMessage() {
        return message;
    }

    public Boolean getValid() {
        return valid;
    }
}
