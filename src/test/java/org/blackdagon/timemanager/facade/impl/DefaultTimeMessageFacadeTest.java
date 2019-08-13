package org.blackdagon.timemanager.facade.impl;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.blackdagon.timemanager.constants.TimeManagerConstants.INVALID_TIME_MESSAGE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class DefaultTimeMessageFacadeTest {

    @Autowired
    private DefaultTimeMessageFacade timeMessageFacade;

    private String startTime;
    private String endTime;

    @BeforeEach
    void setUp() {
        startTime = "10:45";
        endTime = "12:00";
    }

    @Test
    void getTimeMessages() {
        Pair<String, String> messages = timeMessageFacade.getTimeMessages(startTime, endTime);

        assertThat(messages.getLeft(), is("01:15"));
        assertThat(messages.getRight(), is("00:45"));
    }

    @Test
    void getTimeMessagesStartTimeWrong() {
        Pair<String, String> messages = timeMessageFacade.getTimeMessages("Not time", endTime);

        assertThat(messages.getLeft(), is(INVALID_TIME_MESSAGE));
        assertThat(messages.getRight(), is(INVALID_TIME_MESSAGE));
    }

    @Test
    void getTimeMessagesEndTimeWrong() {
        Pair<String, String> messages = timeMessageFacade.getTimeMessages(startTime, "Not time");

        assertThat(messages.getLeft(), is(INVALID_TIME_MESSAGE));
        assertThat(messages.getRight(), is(INVALID_TIME_MESSAGE));
    }

    @Test
    void getTimeMessagesAllTimeWrong() {
        Pair<String, String> messages = timeMessageFacade.getTimeMessages("Not time", "Not time");

        assertThat(messages.getLeft(), is(INVALID_TIME_MESSAGE));
        assertThat(messages.getRight(), is(INVALID_TIME_MESSAGE));
    }

    @Test
    void getTimeMessageForJira() {
        assertThat(timeMessageFacade.getTimeMessageForJira(startTime), is("10h 45m"));
    }
}
