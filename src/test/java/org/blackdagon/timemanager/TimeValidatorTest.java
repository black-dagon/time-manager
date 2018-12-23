package org.blackdagon.timemanager;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

class TimeValidatorTest {

    private TimeValidator timeValidator;
    private String startTime;
    private String endTime;

    @BeforeEach
    public void setUp() {
        timeValidator = new TimeValidator();
        startTime = "10:45";
        endTime = "12:00";
    }

    @Test
    public void testValidate() {
        timeValidator.validate(startTime, endTime);
        assertThat(timeValidator.getValid(), is(true));
    }

    @Test
    public void testValidateNotValid() {
        startTime = "10:490";
        timeValidator.validate(startTime, endTime);
        assertThat(timeValidator.getValid(), is(false));
        assertThat(timeValidator.getMessage(), notNullValue());
    }

}