package org.blackdagon.timemanager.validator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TimeValidatorTest {

    @Test
    void validate() throws Exception {
        String properlyFormattedTime = "10:30";
        assertThat(TimeValidator.validate(properlyFormattedTime), is(properlyFormattedTime));
        String properlyFormattedNegativeTime = "10:30";
        assertThat(TimeValidator.validate(properlyFormattedNegativeTime), is(properlyFormattedNegativeTime));

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("1030");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("10:");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("A:30");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate(":30");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("-10:3");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("1:30");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("-1:30");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("10:3");
        });

        assertThrows(TimeValidatorException.class, () -> {
            TimeValidator.validate("-10:3");
        });

    }

}
