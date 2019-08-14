package org.blackdagon.timemanager.validator;

import org.apache.commons.lang3.StringUtils;

public class TimeValidator {

    public static String validate(String time) throws TimeValidatorException {
        if (StringUtils.contains(time, ":")) {
            String[] formattedString = time.split(":");
            try {
                String hh = formattedString[0];
                String mm = formattedString[1];

                if (hh.length() > 2) {
                    throw new TimeValidatorException("Hours have more than 2 characters");
                }

                if (hh.length() <= 0) {
                    throw new TimeValidatorException("Hours have less than 1 character");
                }

                if (mm.length() > 2) {
                    throw new TimeValidatorException("Minutes have more than 2 characters");
                }

                if (mm.length() <= 0) {
                    throw new TimeValidatorException("Minutes have less than 1 character");
                }

                Integer.valueOf(hh.charAt(0));
                Integer.valueOf(hh.charAt(1));
                Integer.valueOf(mm.charAt(0));
                Integer.valueOf(mm.charAt(1));

                return time;

            } catch (NullPointerException | NumberFormatException e) {
                throw new TimeValidatorException("Missing part of time");
            }

        } else {
            throw new TimeValidatorException("Missing : sign");
        }
    }

}
